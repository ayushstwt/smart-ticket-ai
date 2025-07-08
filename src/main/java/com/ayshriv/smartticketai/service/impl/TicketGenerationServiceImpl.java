package com.ayshriv.smartticketai.service.impl;

import com.ayshriv.smartticketai.common.PromptLoader;
import com.ayshriv.smartticketai.exception.InvalidInputException;
import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;
import com.ayshriv.smartticketai.service.ITicketGenerationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketGenerationServiceImpl implements ITicketGenerationService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final String promptTemplateText;

    public TicketGenerationServiceImpl(OpenAiChatModel chatModel, ObjectMapper objectMapper) {
        this.chatClient = ChatClient.create(chatModel);
        this.objectMapper = objectMapper;
        this.promptTemplateText = PromptLoader.loadFromClasspath("prompts/ticket-generator.txt");
    }

    public ApiResponse<List<JiraTicketDto>> generateTicketsFromTranscript(String transcript) {

        if (transcript == null || transcript.trim().isEmpty()) {
            throw new InvalidInputException("Transcript cannot be empty.");
        }

        PromptTemplate promptTemplate = new PromptTemplate(promptTemplateText);
        Prompt prompt = promptTemplate.create(Map.of("transcript", transcript));
        String rawResponse = chatClient.prompt(prompt).call().content();

        String cleanJson = rawResponse
                .replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .trim();

        try {
            List<JiraTicketDto> tickets = objectMapper.readValue(cleanJson, new TypeReference<>() {});
            return new ApiResponse<>(true, "Tickets generated successfully", tickets);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response to JSON: " + e.getMessage());
        }
    }




}

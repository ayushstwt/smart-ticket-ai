package com.ayshriv.smartticketai.controller;

import com.ayshriv.smartticketai.repository.DTO.JiraTicketDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class OpenAIController {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public OpenAIController(OpenAiChatModel chatModel, ObjectMapper objectMapper) {
        this.chatClient = ChatClient.create(chatModel);
        this.objectMapper = objectMapper;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<JiraTicketDto>> generateTickets(@RequestBody Map<String, String> request) {
        String transcript = request.get("transcript");

        if (transcript == null || transcript.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String promptTemplateText = """
You are an intelligent assistant that analyzes meeting transcripts and extracts clear, structured tasks.

📌 Your job is to:
- Focus only on relevant action points related to product, engineering, design, QA, etc.
- Ignore greetings, jokes, personal discussions (like coffee/lunch), and off-topic comments.
- Extract only well-formed, actionable work items.

🎯 For each task, return the following fields:
- title: Short task title
- description: Full task explanation
- assignee: Person responsible (if known)
- priority: One of High, Medium, Low (based on urgency and impact)
- dueDate: Deadline or time commitment (null if not mentioned)
- type: One of these based on the nature of work:
  - "bug" → fixing something broken
  - "feature" → building something new
  - "enhancement" → improving an existing feature
  - "documentation" → writing guides, help docs, etc.
  - "testing" → validating or verifying functionality
  - "research" → investigating or exploring ideas
  - "other" → if none match

🚫 Important Rules:
- Do NOT include markdown, backticks, or explanations.
- Output must be a **valid JSON array** of task objects only.
- If assignee or dueDate is unknown, use `null`.

Here is the transcript:
{transcript}
""";



        PromptTemplate promptTemplate = new PromptTemplate(promptTemplateText);
        Prompt prompt = promptTemplate.create(Map.of("transcript", transcript));

        String rawResponse = chatClient.prompt(prompt).call().content();


        String cleanJson = rawResponse
                .replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .trim();

        try {
            List<JiraTicketDto> tickets = objectMapper.readValue(
                    cleanJson, new TypeReference<>() {}
            );
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}

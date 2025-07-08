package com.ayshriv.smartticketai.controller;

import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;
import com.ayshriv.smartticketai.resources.DTO.TranscriptRequestDto;
import com.ayshriv.smartticketai.service.ITicketGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class MeetingTranscriptController {

    @Autowired
    private ITicketGenerationService ticketGenerationService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<List<JiraTicketDto>>> generateFromTranscript(@RequestBody TranscriptRequestDto requestDto) {
        ApiResponse<List<JiraTicketDto>> response = ticketGenerationService.generateTicketsFromTranscript(requestDto.getTranscript());
        return ResponseEntity.ok(response);
    }

}

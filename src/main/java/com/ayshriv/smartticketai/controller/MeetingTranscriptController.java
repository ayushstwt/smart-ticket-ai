package com.ayshriv.smartticketai.controller;

import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;
import com.ayshriv.smartticketai.resources.DTO.TranscriptRequestDto;
import com.ayshriv.smartticketai.service.IFileUploadService;
import com.ayshriv.smartticketai.service.ITicketGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class MeetingTranscriptController {

    @Autowired
    private ITicketGenerationService ticketGenerationService;

    @Autowired
    private IFileUploadService fileUploadService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<List<JiraTicketDto>>> generateFromTranscript(@RequestBody TranscriptRequestDto requestDto) {
        ApiResponse<List<JiraTicketDto>> response = ticketGenerationService.generateTicketsFromTranscript(requestDto.getTranscript());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<JiraTicketDto>>> uploadMeetingFile(@RequestPart("file") MultipartFile file) {
        ApiResponse<List<JiraTicketDto>> response = fileUploadService.handleFileUpload(file);
        return ResponseEntity.ok(response);
    }

}

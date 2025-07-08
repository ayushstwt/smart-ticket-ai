package com.ayshriv.smartticketai.service.impl;

import com.ayshriv.smartticketai.common.TikaUtils;
import com.ayshriv.smartticketai.exception.InvalidInputException;
import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;
import com.ayshriv.smartticketai.service.IFileUploadService;
import com.ayshriv.smartticketai.service.ITicketGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Autowired
    private ITicketGenerationService ticketGenerationService;

    public ApiResponse<List<JiraTicketDto>> handleFileUpload(MultipartFile file) {
        String content;
        try {
            if (file.isEmpty()) {
                throw new InvalidInputException("File is empty.");
            }
            content = TikaUtils.extractText(file);
        } catch (Exception e) {
            throw new InvalidInputException("Failed to process file: " + e.getMessage());
        }
        return ticketGenerationService.generateTicketsFromTranscript(content);
    }
}

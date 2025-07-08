package com.ayshriv.smartticketai.service;

import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileUploadService {
    ApiResponse<List<JiraTicketDto>> handleFileUpload(MultipartFile file);
}

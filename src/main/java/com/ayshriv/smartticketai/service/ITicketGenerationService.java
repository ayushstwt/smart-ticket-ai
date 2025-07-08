package com.ayshriv.smartticketai.service;

import com.ayshriv.smartticketai.resources.DTO.ApiResponse;
import com.ayshriv.smartticketai.resources.DTO.JiraTicketDto;

import java.util.List;

public interface ITicketGenerationService {

    ApiResponse<List<JiraTicketDto>> generateTicketsFromTranscript(String transcript);

}

package com.ayshriv.smartticketai.resources.DTO;

import lombok.Data;

@Data
public class JiraTicketDto {
    private String title;
    private String description;
    private String assignee;
    private String priority;
    private String dueDate;
    private String type;
}

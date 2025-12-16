package com.example.demo.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    // private List<String> toEmail;
    private String toEmail;
    private String subject, content;
}

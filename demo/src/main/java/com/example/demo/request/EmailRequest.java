package com.example.demo.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    private String toEmail;
    private String subject, content;
}

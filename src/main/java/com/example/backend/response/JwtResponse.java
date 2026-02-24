package com.example.backend.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class JwtResponse {
    private String token;
}

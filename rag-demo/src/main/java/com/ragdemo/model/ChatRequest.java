package com.ragdemo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "Message cannot be blank")
    private String message;
    
    private String sessionId;
}


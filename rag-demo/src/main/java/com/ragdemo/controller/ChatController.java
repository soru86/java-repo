package com.ragdemo.controller;

import com.ragdemo.model.ChatRequest;
import com.ragdemo.model.ChatResponse;
import com.ragdemo.service.ChatSessionService;
import com.ragdemo.service.RagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ChatController {

    private final RagService ragService;
    private final ChatSessionService sessionService;

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request.getMessage());
        
        // Get or create session
        String sessionId = request.getSessionId();
        if (sessionId == null || !sessionService.sessionExists(sessionId)) {
            sessionId = sessionService.createSession().getId();
            log.info("Created new session: {}", sessionId);
        }
        
        // Add user message to session history
        sessionService.addMessage(sessionId, "user", request.getMessage());
        
        // Generate response using RAG
        String response = ragService.generateResponse(request.getMessage(), sessionId);
        
        // Get retrieved contexts for transparency
        List<String> retrievedContexts = ragService.getRetrievedContexts(request.getMessage());
        
        // Add assistant response to session history
        sessionService.addMessage(sessionId, "assistant", response);
        
        ChatResponse chatResponse = new ChatResponse(response, sessionId, retrievedContexts);
        return ResponseEntity.ok(chatResponse);
    }

    @GetMapping("/session/{sessionId}/history")
    public ResponseEntity<List<String>> getSessionHistory(@PathVariable String sessionId) {
        List<String> history = sessionService.getSessionHistory(sessionId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/session/new")
    public ResponseEntity<String> createNewSession() {
        String sessionId = sessionService.createSession().getId();
        return ResponseEntity.ok(sessionId);
    }
}


package com.ragdemo.service;

import com.ragdemo.model.ChatSession;
import com.ragdemo.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatSessionService {

    private final ChatSessionRepository sessionRepository;

    public ChatSession createSession() {
        ChatSession session = new ChatSession();
        session.setId(UUID.randomUUID().toString());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        session.setMessages(new ArrayList<>());
        
        ChatSession saved = sessionRepository.save(session);
        log.info("Created new chat session: {}", saved.getId());
        return saved;
    }

    public ChatSession getSession(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found: " + sessionId));
    }

    @Transactional
    public ChatSession addMessage(String sessionId, String role, String content) {
        ChatSession session = getSession(sessionId);
        
        // Format: "role: content"
        String message = String.format("%s: %s", role, content);
        session.getMessages().add(message);
        session.setUpdatedAt(LocalDateTime.now());
        
        ChatSession updated = sessionRepository.save(session);
        log.info("Added message to session: {}", sessionId);
        return updated;
    }

    public List<String> getSessionHistory(String sessionId) {
        ChatSession session = getSession(sessionId);
        return new ArrayList<>(session.getMessages());
    }

    public boolean sessionExists(String sessionId) {
        return sessionRepository.existsById(sessionId);
    }
}


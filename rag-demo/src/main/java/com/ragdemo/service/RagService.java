package com.ragdemo.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.retriever.Retriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RagService {

    private final ChatLanguageModel chatModel;
    private final Retriever<TextSegment> contentRetriever;

    public String generateResponse(String userQuery, String sessionId) {
        log.info("Generating response for session: {}", sessionId);
        
        // Retrieve relevant context from Vector DB
        List<TextSegment> relevantSegments = contentRetriever.findRelevant(userQuery);
        
        log.info("Retrieved {} relevant content chunks", relevantSegments.size());
        
        // Build augmented prompt with context
        StringBuilder augmentedPrompt = new StringBuilder();
        
        if (!relevantSegments.isEmpty()) {
            augmentedPrompt.append("Based on the following context from the knowledge base:\n\n");
            for (TextSegment segment : relevantSegments) {
                augmentedPrompt.append("- ").append(segment.text()).append("\n\n");
            }
            augmentedPrompt.append("Please answer the following question based on the context provided above.\n\n");
        }
        
        augmentedPrompt.append("Question: ").append(userQuery);
        
        // Generate response using LLM
        UserMessage userMessage = new UserMessage(augmentedPrompt.toString());
        Response<AiMessage> response = chatModel.generate(userMessage);
        
        String generatedResponse = response.content().text();
        log.info("Generated response length: {} characters", generatedResponse.length());
        
        return generatedResponse;
    }

    public List<String> getRetrievedContexts(String userQuery) {
        List<TextSegment> relevantSegments = contentRetriever.findRelevant(userQuery);
        
        return relevantSegments.stream()
                .map(TextSegment::text)
                .collect(Collectors.toList());
    }
}


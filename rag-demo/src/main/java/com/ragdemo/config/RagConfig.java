package com.ragdemo.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RagConfig {

    @Value("${ollama.base-url:http://ollama:11434}")
    private String ollamaBaseUrl;

    @Value("${ollama.model:deepseek-r1}")
    private String ollamaModel;

    @Value("${chroma.base-url:http://chroma:8000}")
    private String chromaBaseUrl;

    @Value("${chroma.collection-name:rag-documents}")
    private String chromaCollectionName;

    @Value("${rag.top-k:3}")
    private int topK;

    @Value("${rag.min-score:0.6}")
    private double minScore;

    @Bean
    public EmbeddingModel embeddingModel() {
        // Using local embedding model for offline operation
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return ChromaEmbeddingStore.builder()
                .baseUrl(chromaBaseUrl)
                .collectionName(chromaCollectionName)
                .build();
    }

    @Bean
    public OllamaChatModel chatModel() {
        return OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModel)
                .temperature(0.7)
                .build();
    }

    @Bean
    public Retriever<TextSegment> contentRetriever(EmbeddingStore<TextSegment> embeddingStore, 
                                                   EmbeddingModel embeddingModel) {
        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, topK, minScore);
    }
}


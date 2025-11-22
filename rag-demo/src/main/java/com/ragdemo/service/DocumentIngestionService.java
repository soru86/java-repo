package com.ragdemo.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentIngestionService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    private static final String UPLOAD_DIR = "uploads";

    public void ingestPdf(MultipartFile file) throws IOException {
        log.info("Starting PDF ingestion for file: {}", file.getOriginalFilename());

        // Create upload directory if it doesn't exist (using absolute path)
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // Save uploaded file temporarily
        Path tempFile = uploadPath.resolve(file.getOriginalFilename());

        // Ensure parent directory exists
        Files.createDirectories(tempFile.getParent());

        // Copy file content using Files.copy for better error handling
        Files.copy(file.getInputStream(), tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        try {
            // Parse PDF document
            DocumentParser documentParser = new ApachePdfBoxDocumentParser();
            Document document = FileSystemDocumentLoader.loadDocument(tempFile, documentParser);

            log.info("PDF parsed successfully. Content length: {} characters", document.text().length());

            // Split document into chunks
            DocumentSplitter splitter = DocumentSplitters.recursive(300, 50);
            List<TextSegment> segments = splitter.split(document);

            log.info("Document split into {} segments", segments.size());

            // Generate embeddings and store them
            List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

            log.info("Generated {} embeddings", embeddings.size());

            embeddingStore.addAll(embeddings, segments);

            log.info("Successfully ingested PDF and stored embeddings in Vector DB");

        } finally {
            // Clean up temporary file
            Files.deleteIfExists(tempFile);
        }
    }

    public void ingestPdfFromPath(String filePath) throws IOException {
        log.info("Starting PDF ingestion from path: {}", filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        // Parse PDF document
        DocumentParser documentParser = new ApachePdfBoxDocumentParser();
        Document document = FileSystemDocumentLoader.loadDocument(file.toPath(), documentParser);

        log.info("PDF parsed successfully. Content length: {} characters", document.text().length());

        // Split document into chunks
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 50);
        List<TextSegment> segments = splitter.split(document);

        log.info("Document split into {} segments", segments.size());

        // Generate embeddings and store them
        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        log.info("Generated {} embeddings", embeddings.size());

        embeddingStore.addAll(embeddings, segments);

        log.info("Successfully ingested PDF and stored embeddings in Vector DB");
    }
}

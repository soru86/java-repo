package com.ragdemo.controller;

import com.ragdemo.service.DocumentIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentIngestionService documentIngestionService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadPdf(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("error", "File is empty");
                return ResponseEntity.badRequest().body(response);
            }

            if (!file.getContentType().equals("application/pdf")) {
                response.put("error", "Only PDF files are supported");
                return ResponseEntity.badRequest().body(response);
            }

            documentIngestionService.ingestPdf(file);
            response.put("message", "PDF uploaded and processed successfully");
            response.put("filename", file.getOriginalFilename());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error processing PDF", e);
            response.put("error", "Failed to process PDF: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}


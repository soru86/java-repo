package com.dvtsoftware.airline.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.jackson.DatabindCodec;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit 5 extension to configure Jackson for LocalDateTime support.
 * This runs before any tests to ensure Jackson is properly configured.
 */
public class JacksonConfigurationExtension implements BeforeAllCallback {
    
    private static volatile boolean configured = false;
    private static final Object lock = new Object();
    
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!configured) {
            synchronized (lock) {
                if (!configured) {
                    configureJackson();
                    configured = true;
                }
            }
        }
    }
    
    private static void configureJackson() {
        try {
            // Get the mapper instance from Vert.x
            ObjectMapper mapper = DatabindCodec.mapper();
            
            // Find and register all modules (should find JavaTimeModule automatically)
            mapper.findAndRegisterModules();
            
            // Explicitly register JavaTimeModule to ensure it's there
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            
            // Also configure the pretty mapper
            try {
                @SuppressWarnings("deprecation")
                ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
                if (prettyMapper != null && prettyMapper != mapper) {
                    prettyMapper.findAndRegisterModules();
                    prettyMapper.registerModule(new JavaTimeModule());
                    prettyMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                }
            } catch (Exception e) {
                // Pretty mapper might not be available, ignore
            }
        } catch (Exception e) {
            System.err.println("Failed to configure Jackson: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Call this method to ensure Jackson is configured.
     * Can be called multiple times safely.
     */
    public static void ensureConfigured() {
        if (!configured) {
            synchronized (lock) {
                if (!configured) {
                    configureJackson();
                    configured = true;
                }
            }
        }
    }
}


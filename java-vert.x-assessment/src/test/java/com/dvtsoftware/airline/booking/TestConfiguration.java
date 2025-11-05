package com.dvtsoftware.airline.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * Test configuration to set up Jackson for LocalDateTime support in tests.
 * This must be configured before any Vert.x code uses JsonObject.mapFrom().
 */
public class TestConfiguration {
    
    private static volatile boolean configured = false;
    private static final Object lock = new Object();
    
    static {
        // Configure immediately when class is loaded, before any other code runs
        configure();
    }
    
    public static void configure() {
        if (configured) {
            return; // Already configured
        }
        
        synchronized (lock) {
            if (configured) {
                return; // Double-check after acquiring lock
            }
            
            try {
                // Get the mapper instance - this should be the shared instance
                ObjectMapper mapper = DatabindCodec.mapper();
                
                // Find and register all modules (should auto-detect JavaTimeModule)
                mapper.findAndRegisterModules();
                
                // Explicitly register JavaTimeModule to ensure it's there
                // This is safe to call multiple times
                JavaTimeModule javaTimeModule = new JavaTimeModule();
                mapper.registerModule(javaTimeModule);
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                
                // Force mapper to update its serializers by accessing serializer provider
                // This ensures the configuration is actually applied
                try {
                    mapper.getSerializerProvider();
                    // Create a test object to force serializer initialization
                    mapper.canSerialize(java.time.LocalDateTime.class);
                } catch (Exception e) {
                    // Ignore - just trying to force initialization
                }
                
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
                
                configured = true;
            } catch (Exception e) {
                System.err.println("Warning: Failed to configure TestConfiguration: " + e.getMessage());
                e.printStackTrace();
                // Don't mark as configured so we can retry
            }
        }
    }
}


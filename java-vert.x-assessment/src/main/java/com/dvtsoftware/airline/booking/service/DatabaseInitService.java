package com.dvtsoftware.airline.booking.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Pool;

public class DatabaseInitService extends DatabaseService {
    public DatabaseInitService(Vertx vertx) {
    	super(vertx);
    }

    public Future<Void> initializeDatabase() {
        Promise<Void> promise = Promise.promise();
        
        try {
            // Read SQL files
            String schemaSql = Files.readString(Paths.get("src/main/resources/schema.sql"));
            String dataSql = Files.readString(Paths.get("src/main/resources/data.sql"));

            // Split into individual statements (naive split by semicolon)
            List<String> schemaStatements = Arrays.stream(schemaSql.split(";"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            List<String> dataStatements = Arrays.stream(dataSql.split(";"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            // Execute schema statements sequentially
            executeStatementsSequentially(schemaStatements)
                    .compose(v -> executeStatementsSequentially(dataStatements))
                    .onComplete(promise);
                    
        } catch (Exception e) {
            promise.fail("Failed to read SQL files: " + e.getMessage());
        }
        
        return promise.future();
    }

    private Future<Void> executeStatementsSequentially(List<String> statements) {
        Promise<Void> promise = Promise.promise();
        executeNext(statements, 0, promise);
        return promise.future();
    }

    private void executeNext(List<String> statements, int idx, Promise<Void> promise) {
        if (idx >= statements.size()) {
            promise.complete();
            return;
        }
        
        String statement = statements.get(idx);
        if (statement.trim().isEmpty()) {
            executeNext(statements, idx + 1, promise);
            return;
        }
        
        client.query(statement).execute().onComplete(ar -> {
            if (ar.failed()) {
                System.err.println("Failed to execute statement: " + statement);
                System.err.println("Error: " + ar.cause().getMessage());
                promise.fail(ar.cause());
            } else {
                executeNext(statements, idx + 1, promise);
            }
        });
    }

    public Pool getClient() {
        return client;
    }
}
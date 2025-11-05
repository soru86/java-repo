# Multi-stage Dockerfile for Airline Booking System

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install wget for health check
RUN apk add --no-cache wget

# Create non-root user
RUN addgroup -S airline && adduser -S airline -G airline

# Copy the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership
RUN chown airline:airline app.jar

# Switch to non-root user
USER airline

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


#!/bin/bash

echo "========================================="
echo "RAG Demo Application Startup Script"
echo "========================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker and try again."
    exit 1
fi

# Start services
echo "Starting Docker Compose services..."
docker-compose up -d

# Wait for services to be healthy
echo "Waiting for services to be ready..."
sleep 10

# Check Ollama health
echo "Checking Ollama service..."
until docker exec rag-ollama curl -f http://localhost:11434/api/tags > /dev/null 2>&1; do
    echo "Waiting for Ollama to be ready..."
    sleep 5
done

# Pull Deepseek R1 model (or fallback to deepseek-chat)
echo "Pulling Deepseek R1 model..."
if docker exec rag-ollama ollama pull deepseek-r1 2>/dev/null; then
    echo "✓ Deepseek R1 model pulled successfully"
else
    echo "⚠ Deepseek R1 not available, trying deepseek-chat..."
    if docker exec rag-ollama ollama pull deepseek-chat 2>/dev/null; then
        echo "✓ Deepseek-chat model pulled successfully"
        echo "⚠ Note: Update application.yml to use 'deepseek-chat' as the model name"
    else
        echo "⚠ Could not pull model. Please check Ollama logs: docker logs rag-ollama"
    fi
fi

# Check Chroma health (v1 API for Chroma 0.4.22)
echo "Checking Chroma service..."
until curl -f http://localhost:8000/api/v1/heartbeat > /dev/null 2>&1; do
    echo "Waiting for Chroma to be ready..."
    sleep 5
done
echo "✓ Chroma is ready"

# Check application health
echo "Checking application health..."
sleep 15
until curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; do
    echo "Waiting for application to be ready..."
    sleep 5
done
echo "✓ Application is ready"

echo ""
echo "========================================="
echo "All services are up and running!"
echo "========================================="
echo ""
echo "Services:"
echo "  - Application: http://localhost:8080"
echo "  - Chroma: http://localhost:8000"
echo "  - Ollama: http://localhost:11434"
echo ""
echo "Next steps:"
echo "  1. Upload a PDF: curl -X POST http://localhost:8080/api/documents/upload -F 'file=@your-document.pdf'"
echo "  2. Start chatting: curl -X POST http://localhost:8080/api/chat/message -H 'Content-Type: application/json' -d '{\"message\":\"Your question\"}'"
echo ""
echo "To view logs: docker-compose logs -f"
echo "To stop: docker-compose down"
echo ""


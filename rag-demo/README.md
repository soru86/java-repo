# RAG Demo Application

A conversational AI service implementing Retrieval-Augmented Generation (RAG) using Spring Boot, LangChain4j, Deepseek R1 LLM via Ollama, and Chroma Vector Database.

## Architecture

- **Spring Boot**: Microservices framework
- **LangChain4j**: RAG pipeline implementation
- **Ollama**: Local LLM hosting (Deepseek R1)
- **Chroma**: Vector database for embeddings
- **Docker & Docker Compose**: Containerization and orchestration

## Features

1. **PDF Document Ingestion**: Upload and process PDF documents
2. **Vector Embeddings**: Generate and store embeddings in Chroma
3. **RAG Pipeline**: Retrieve relevant context and augment LLM prompts
4. **Chat Interface**: RESTful API for conversational interactions
5. **Session Management**: Maintain chat history across conversations
6. **Offline Operation**: Local LLM execution via Ollama

## Prerequisites

- Docker and Docker Compose installed
- Java 17+ (for local development)
- Maven 3.6+ (for local development)

## Quick Start

### 1. Build and Start Services

```bash
docker-compose up -d
```

This will start:
- Chroma Vector DB (port 8000)
- Ollama LLM Engine (port 11434)
- Spring Boot Application (port 8080)

### 2. Pull Deepseek R1 Model

After Ollama container is running, pull the Deepseek R1 model:

```bash
docker exec rag-ollama ollama pull deepseek-r1
```

**Note**: If `deepseek-r1` is not available, you can use an alternative model like `deepseek-chat`:

```bash
docker exec rag-ollama ollama pull deepseek-chat
```

Then update the `ollama.model` property in `application.yml` to `deepseek-chat`.

### 3. Upload a PDF Document

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -F "file=@/path/to/your/document.pdf"
```

### 4. Start Chatting

```bash
curl -X POST http://localhost:8080/api/chat/message \
  -H "Content-Type: application/json" \
  -d '{
    "message": "What is the main topic of the document?"
  }'
```

## API Endpoints

### Document Management

- `POST /api/documents/upload` - Upload and ingest a PDF document
  - Form data: `file` (multipart/form-data)

### Chat

- `POST /api/chat/message` - Send a message and get RAG-enhanced response
  - Body: `{ "message": "your question", "sessionId": "optional" }`
  - Response: `{ "response": "...", "sessionId": "...", "retrievedContexts": [...] }`

- `POST /api/chat/session/new` - Create a new chat session
  - Response: Session ID string

- `GET /api/chat/session/{sessionId}/history` - Get chat history for a session
  - Response: Array of messages

### Health & Monitoring

- `GET /actuator/health` - Application health check
- `GET /h2-console` - H2 Database console (for debugging)

## Configuration

Key configuration properties in `application.yml`:

```yaml
ollama:
  base-url: http://ollama:11434
  model: deepseek-r1

chroma:
  base-url: http://chroma:8000
  collection-name: rag-documents

rag:
  top-k: 3          # Number of relevant chunks to retrieve
  min-score: 0.6    # Minimum similarity score for retrieval
```

## Development

### Local Development (without Docker)

1. Start Chroma and Ollama using Docker Compose:
```bash
docker-compose up chroma ollama -d
```

2. Pull the model:
```bash
docker exec rag-ollama ollama pull deepseek-r1
```

3. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

### Building the Application

```bash
mvn clean package
```

## Project Structure

```
src/main/java/com/ragdemo/
├── RagDemoApplication.java          # Main application class
├── config/
│   └── RagConfig.java               # RAG configuration and beans
├── controller/
│   ├── ChatController.java          # Chat API endpoints
│   └── DocumentController.java      # Document upload endpoints
├── model/
│   ├── ChatMessage.java             # Chat message model
│   ├── ChatRequest.java             # Request DTO
│   ├── ChatResponse.java            # Response DTO
│   └── ChatSession.java             # Session entity
├── repository/
│   └── ChatSessionRepository.java   # Session repository
└── service/
    ├── ChatSessionService.java      # Session management
    ├── DocumentIngestionService.java # PDF processing
    └── RagService.java              # RAG pipeline
```

## Troubleshooting

### Ollama Model Not Found

If `deepseek-r1` is not available, check available models:
```bash
docker exec rag-ollama ollama list
```

Use an available model and update `application.yml`.

### Chroma Connection Issues

Ensure Chroma is healthy:
```bash
docker exec rag-chroma wget -O- http://localhost:8000/api/v1/heartbeat
```

### Application Won't Start

Check logs:
```bash
docker-compose logs rag-app
```

Ensure all dependencies are healthy:
```bash
docker-compose ps
```

## License

This is a demo application for educational purposes.


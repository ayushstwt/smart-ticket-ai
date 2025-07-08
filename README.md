# Smart Ticket AI

Smart Ticket AI is an intelligent meeting assistant built using **Spring Boot**, **Spring AI**, and **OpenAI API**.  
It analyzes meeting transcripts and generates well-structured, actionable tickets in a JIRA-ready format.

## Features

- Accepts meeting notes via text input or uploaded files
- Extracts actionable items using OpenAI GPT
- Classifies tasks into ticket types: `bug`, `feature`, `enhancement`, `documentation`, `testing`, `research`
- Detects and includes:
  - Assignee (if mentioned)
  - Priority (High, Medium, Low)
  - Due date (if mentioned)
- Generates short meeting summary (2–3 lines)
- Returns clean JSON array response
- Global exception handling and input validation
- Modular and scalable service-layer structure

## Tech Stack

| Layer            | Technology                      |
|------------------|----------------------------------|
| Backend          | Java 17, Spring Boot 3.x         |
| AI Integration   | Spring AI 1.0.0, OpenAI API      |
| File Parsing     | Apache Tika                      |
| JSON Handling    | Jackson (ObjectMapper)           |
| API Testing      | Postman, Swagger (optional)      |
| Build Tool       | Maven                            |
| Deployment Ready | Docker (optional)                |

## API Endpoints

### 1. Generate Tickets from Transcript

**Endpoint:**  
`POST /api/tickets/generate`

**Request Body:**
```json
{
  "transcript": "Full meeting transcript text here"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Tickets and summary generated successfully",
  "summary": "Short summary of the meeting",
  "tickets": [
    {
      "title": "Fix login issue",
      "description": "Ayush to fix session redirect bug",
      "assignee": "Ayush",
      "priority": "High",
      "dueDate": "2025-07-12",
      "type": "bug"
    }
  ]
}
```

### 2. Upload Transcript File

**Endpoint:**  
`POST /api/upload`

**Content-Type:**  
`multipart/form-data`

**Form Field:**
- `file` — Upload `.txt`, `.pdf`, or `.docx`

**Response:**
```json
{
  "success": true,
  "message": "Tickets and summary generated successfully",
  "summary": "Team discussed login bug fixes and UI updates",
  "tickets": [
    {
      "title": "Fix email validation",
      "description": "Priya to address QA-reported email validation issue",
      "assignee": "Priya",
      "priority": "High",
      "dueDate": null,
      "type": "bug"
    }
  ]
}
```

## Sample Test File

Download the test file to try the upload API:  
[meeting-transcript.txt](sandbox:/mnt/data/meeting-transcript.txt)

## How It Works

- User submits raw meeting text or uploads a document
- Application uses OpenAI to:
  - Summarize the meeting
  - Extract task-related data
  - Generate structured tickets with classification
- Response is returned in clean JSON format

## Future Enhancements

- Direct JIRA ticket creation via JIRA REST API
- Slack/Email integration
- Support for audio transcript parsing
- Role-based authentication and user tagging
- Frontend UI (React)

# TASK-MANAGER-BACKEND

A robust Spring Boot application for managing and executing system tasks through a REST API with MongoDB storage. Perfect for automating repetitive system operations with execution history tracking.

![Task Service Dashboard]
PUT:<img width="1440" alt="put" src="https://github.com/user-attachments/assets/4b5725ef-6414-467f-aa3c-d4e8f7ae4747" />
GET:<img width="1440" alt="Get" src="https://github.com/user-attachments/assets/5acd92e0-995c-4e69-9a43-c378e2ab849f" />
EXECUTE:<img width="1440" alt="exe" src="https://github.com/user-attachments/assets/a0a4dae1-4dab-43c8-8e9d-2ca3f1d04a4e" />
DELETE:<img width="1440" alt="delete" src="https://github.com/user-attachments/assets/6bd7879f-d434-40e6-a31e-6650764a167d" />
GET (find) tasks by name:<img width="1440" alt="name" src="https://github.com/user-attachments/assets/e4b4d936-5809-4d8a-825f-0508202462c8" />

CLUSTER:<img width="1440" alt="execute" src="https://github.com/user-attachments/assets/47b1f8df-e7c6-45a7-9937-08b9c6e93bc9" />


## Features 

- CRUD Operations**: Create, read, update, and delete tasks
- Command Execution**: Run system commands through tasks
- Execution History**: Track execution timestamps and outputs
- Search Functionality**: Find tasks by name (case-insensitive)
- Safety Checks**: Basic command validation against dangerous operations
- Cross-Platform**: Works on Windows and Unix-like systems

## Technology Stack 🛠

- Backend**: Spring Boot 3.x
- Database**: MongoDB
- Build Tool**: Maven
- Java Version**: 17+

## API Documentation 

### Base URL
`http://localhost:8080/tasks`

### Endpoints

| Method | Endpoint                | Description                          | Example |
|--------|-------------------------|--------------------------------------|---------|
| GET    | `/`                     | Get all tasks or specific task by ID | `GET /tasks?taskId=123` |
| PUT    | `/`                     | Create/update a task                 | [See example](#create-a-task) |
| DELETE | `/{id}`                 | Delete a task                        | `DELETE /tasks/123` |
| GET    | `/search?name={name}`   | Search tasks by name                 | `GET /tasks/search?name=backup` |
| PUT    | `/{id}/execute`         | Execute a task                       | [See example](#execute-a-task) |

## Data Model 📊

### Task Structure
```json
{
  "id": "65a1b2c3d4e5f6g7h8i9j0k",
  "name": "Daily Backup",
  "owner": "admin",
  "command": "tar -czf backup.tar.gz /var/www",
  "taskExecutions": [
    {
      "startTime": "2023-12-01T10:15:30.00Z",
      "endTime": "2023-12-01T10:16:45.00Z",
      "output": "backup.tar.gz created successfully"
    }
  ]
}
```

Cloning the repo:

Step-1: git clone https://github.com/yourusername/task-service.git
cd task

Step-2: Build the project:
mvn clean install

Step-3: Run the application
java -jar target/task-service.jar

Step-4: The application will be available at http://localhost:8080

Configuration 
MongoDB Connection

Edit src/main/resources/application.properties:
spring.data.mongodb.uri=mongodb://username:password@host:port/database

Server Port
server.port=8080


Security 
Blocked Commands
The service automatically blocks commands containing:

1. File deletion (rm)
2. System control (shutdown, reboot)
3. Filesystem operations (mkfs)
4. Command chaining (;, |, &&)

Recommended Security Enhancements
1. Add JWT Authentication
2. Implement Role-Based Access Control
3. Enable HTTPS
4. Add rate limiting
5. Implement more robust command validation


Project Structure
src/
├── main/
│   ├── java/org/akash/task/
│   │   ├── controller/    # API endpoints
│   │   ├── model/         # Data models
│   │   ├── repository/    # MongoDB interfaces
│   │   ├── service/       # Business logic
│   │   └── TaskApplication.java
│   └── resources/         # Config files
└── test/                  # Unit tests


Running Tests

mvn test

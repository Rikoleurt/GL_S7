# GL_S7

# Exercise 25 - Spring Todo-Backend

## What has been implemented

The goal of this exercise was to implement `TagController` and the following operations:

- `GET /tags/` — retrieve all tags
- `POST /tags/` — create a new tag
- `DELETE /tags/` — delete all tags
- `GET /tags/{id}` — get a tag by ID
- `PATCH /tags/{id}` — update a tag
- `DELETE /tags/{id}` — delete a tag by ID
- `GET /tags/{id}/todos/` — retrieve all todos associated with a tag

All operations follow the specifications provided in SwaggerHub and were validated using the provided testing tool.

## How to run the test

1. Start the Spring Boot application (on your IDE/Terminal)
2. Use any research tool (except Chrome for CORS reason)
3. Go to this link : http://todospecs.thing.zone/?http://localhost:8080
4. The tool will verify the implementation by calling GET, POST, DELETE, UPDATE operations

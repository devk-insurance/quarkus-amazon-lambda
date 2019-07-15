package com.deemkeen.quarkus.lambda;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class HelloDynamoResponse {

    private final String id;
    private int status;
    private String message = "";
    private Long updatedAt = 0L;

    public HelloDynamoResponse(String id, int status) {
        this.id = id;
        this.status = status;
    }

    public HelloDynamoResponse(String id, int status, String message, Long updatedAt) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HelloDynamoResponse{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

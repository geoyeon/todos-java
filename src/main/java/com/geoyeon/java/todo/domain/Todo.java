package com.geoyeon.java.todo.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Todo {
    @Id
    private String id;

    private String title;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isCompleted = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Todo(String title, String memo, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

package com.geoyeon.java.todo.dto;

import com.geoyeon.java.todo.domain.Todo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TodoListResponse {
    private List<Todo> list;
    private long totalCount;
}

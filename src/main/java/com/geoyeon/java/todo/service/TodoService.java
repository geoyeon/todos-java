package com.geoyeon.java.todo.service;

import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo createTodo(TodoCreateRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setMemo(request.getMemo());
        todo.setStartDate(request.getStartDate());
        todo.setEndDate(request.getEndDate());

        return this.todoRepository.save(todo);
    }
}

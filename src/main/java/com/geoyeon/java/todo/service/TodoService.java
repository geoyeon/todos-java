package com.geoyeon.java.todo.service;

import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.dto.TodoUpdateRequest;
import com.geoyeon.java.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public List<Todo> getTodos() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return this.todoRepository.findAll(sort);
    }

    public Optional<Todo> getTodo(String id) {
        return this.todoRepository.findById(id);
    }

    public boolean updateTodo(String id, TodoUpdateRequest request) {
        Optional<Todo> originalTodo = this.getTodo(id);

        originalTodo.ifPresentOrElse(todo -> {

            if (request.getTitle() != null) {
                todo.setTitle(request.getTitle());
            }

            if (request.getMemo() != null) {
                todo.setMemo(request.getMemo());
            }

            if (request.getStartDate() != null) {
                todo.setStartDate(request.getStartDate());
            }

            if (request.getEndDate() != null) {
                todo.setEndDate(request.getEndDate());
            }

            if (request.getIsComplete() != null) {
                todo.setCompleted(request.getIsComplete());
            }

            log.info("before save - {}", todo);

            this.todoRepository.save(todo);
        },
                () -> {
                    throw new IllegalStateException("Value not found");

                });

        return true;
    }
}

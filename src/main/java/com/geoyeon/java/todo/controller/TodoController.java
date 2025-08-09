package com.geoyeon.java.todo.controller;

import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableMongoAuditing
@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<Todo> create(@RequestBody @Valid TodoCreateRequest request) {
        log.info("Post : Todo Create -: {}", request.toString());

//        return ResponseEntity.ok().body("todo Create");
        Todo createdTodo = this.todoService.createTodo(request);

        return ResponseEntity.ok().body(createdTodo);
    }
}

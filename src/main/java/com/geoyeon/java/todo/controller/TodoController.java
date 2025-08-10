package com.geoyeon.java.todo.controller;

import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.dto.TodoUpdateRequest;
import com.geoyeon.java.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getTodos() {
        log.info("Get : List");

        List<Todo> todos = this.todoService.getTodos();

        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Todo>> getTodo(@PathVariable("id") String id) {
        log.info("Get : Todo Id - {}", id);

        Optional<Todo> todo = this.todoService.getTodo(id);

        return ResponseEntity.ok().body(todo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable("id") String id, @RequestBody @Valid TodoUpdateRequest request) {
        log.info("Update : todo : {}", request.toString());

        try {
            boolean result = this.todoService.updateTodo(id, request);

            return ResponseEntity.ok().body("true");
        } catch (Exception e) {
            log.info("error - {}", e.toString());
            return ResponseEntity.ok().body("fail");
        }
    }
}

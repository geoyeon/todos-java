package com.geoyeon.java.todo.repository;

import com.geoyeon.java.todo.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

}

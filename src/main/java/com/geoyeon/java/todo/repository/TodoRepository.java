package com.geoyeon.java.todo.repository;

import com.geoyeon.java.todo.domain.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, ObjectId> {
}



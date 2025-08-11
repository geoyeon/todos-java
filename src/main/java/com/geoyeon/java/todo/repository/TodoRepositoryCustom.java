package com.geoyeon.java.todo.repository;

import com.geoyeon.java.todo.domain.Todo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface TodoRepositoryCustom {
    List<Todo> findByWherePaginationWithQuerydsl(Query query);

    Long totalCount();

    long countWithCondition(Query query);
}

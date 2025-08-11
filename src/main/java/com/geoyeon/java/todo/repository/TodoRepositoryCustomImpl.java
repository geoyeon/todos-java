package com.geoyeon.java.todo.repository;

import com.geoyeon.java.todo.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public List<Todo> findByWherePaginationWithQuerydsl(Query query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        query.with(sort);

        return mongoTemplate.find(query, Todo.class);
    }

    public Long totalCount() {
        return mongoTemplate.estimatedCount(Todo.class);
    }

    public long countWithCondition(Query query) {
        return mongoTemplate.count(query, Todo.class);
    }
}

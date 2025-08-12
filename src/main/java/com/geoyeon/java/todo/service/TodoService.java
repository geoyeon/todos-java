package com.geoyeon.java.todo.service;

import com.geoyeon.java.todo.common.ErrorCode;
import com.geoyeon.java.todo.common.TodoException;
import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.dto.TodoListResponse;
import com.geoyeon.java.todo.dto.TodoUpdateRequest;
import com.geoyeon.java.todo.repository.TodoRepository;
import com.geoyeon.java.todo.repository.TodoRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoRepositoryCustom todoRepositoryCustom;

    public Todo createTodo(TodoCreateRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setMemo(request.getMemo());
        todo.setStartDate(request.getStartDate());
        todo.setEndDate(request.getEndDate());

        return this.todoRepository.save(todo);
    }

    public TodoListResponse getTodos(int page, Boolean isComplete, String search) {
        Query query = new Query();

        int limit = 10;
        int skip = (page - 1) * limit;

        query.skip(skip);
        query.limit(limit);

        if (isComplete != null) {
            if (isComplete) query.addCriteria(Criteria.where("isComplete").is(isComplete));
        }

        if (search != null && !search.isBlank()) {
            List<Criteria> orSearchCondition = new ArrayList<>();
            orSearchCondition.add(Criteria.where("title").regex(search, "i"));
            orSearchCondition.add(Criteria.where("memo").regex(search, "i"));
            query.addCriteria(new Criteria().orOperator(orSearchCondition));
        }

        log.info("query : {}", query);

        long totalCount = (isComplete == null && search == null) ? this.todoRepositoryCustom.totalCount() : this.todoRepositoryCustom.countWithCondition(query);

        List<Todo> list = this.todoRepositoryCustom.findByWherePaginationWithQuerydsl(query);

        return TodoListResponse.builder().list(list).totalCount(totalCount).build();
    }

    public Optional<Todo> getTodo(String id) {
        return this.todoRepository.findById(new ObjectId(id));
    }

    // 트랜잭션 테스트 용도
    @Transactional
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
                throw new TodoException(ErrorCode.NOT_FOUND_TODO);
            });

        return true;
    }
}

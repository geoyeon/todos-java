package com.geoyeon.java.todo;

import com.geoyeon.java.todo.domain.Todo;
import com.geoyeon.java.todo.dto.TodoCreateRequest;
import com.geoyeon.java.todo.dto.TodoListResponse;
import com.geoyeon.java.todo.repository.TodoRepository;
import com.geoyeon.java.todo.repository.TodoRepositoryCustom;
import com.geoyeon.java.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoRepositoryCustom todoRepositoryCustom;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @DisplayName("TODO 생성 성공")
    public void successTodoCreate() {
        String title = "제목";
        String memo = "메모";
        LocalDateTime startDate = LocalDateTime.of(2015, 8, 12, 22, 57);
        LocalDateTime endDate = LocalDateTime.of(2015, 8, 12, 22, 58);
        TodoCreateRequest request = new TodoCreateRequest(title, memo, startDate, endDate);

        Todo mockTodo = new Todo(request.getTitle(), request.getMemo(), request.getStartDate(), request.getEndDate());

        doReturn(mockTodo).when(this.todoRepository).save(any(Todo.class));

        var sut = new TodoService(this.todoRepository, this.todoRepositoryCustom);

        Todo savedTodo = sut.createTodo(request);

        assertThat(savedTodo.getTitle()).isEqualTo("제목");
        assertThat(savedTodo.getMemo()).isEqualTo("메모");
        assertThat(savedTodo.getStartDate()).isEqualTo(LocalDateTime.of(2015, 8, 12, 22, 57));
        assertThat(savedTodo.getEndDate()).isEqualTo(LocalDateTime.of(2015, 8, 12, 22, 58));
    }

    @Test
    @DisplayName("TODO 목록 조회")
    public void successTodoList() {
        int page = 1;

//        TodoListResponse response = new TodoListResponse();
        List<Todo> mockTodos = new ArrayList<Todo>();
        long mockTotalCount = 0;

        doReturn(mockTotalCount).when(this.todoRepositoryCustom).totalCount();
        doReturn(mockTodos).when(this.todoRepositoryCustom).findByWherePaginationWithQuerydsl(any(Query.class));

        var sut = new TodoService(this.todoRepository, this.todoRepositoryCustom);

        TodoListResponse response = sut.getTodos(page, null, null);

        assertThat(response.getList()).isEqualTo(mockTodos);
        assertThat(response.getTotalCount()).isEqualTo(mockTotalCount);

        verify(this.todoRepositoryCustom, times(1)).totalCount();
        verify(this.todoRepositoryCustom, times(1)).findByWherePaginationWithQuerydsl(any(Query.class));
    }
}

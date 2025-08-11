package com.geoyeon.java.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoCreateRequest {
    @NotBlank(message = "제목은 필수입니다.")
//    @Min(value = 3, message = "제목은 3자이상 필수입니다.")
    private String title;

    private String memo;

    @NotNull(message = "시작일자를 입력해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "종료일자를 입력해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}

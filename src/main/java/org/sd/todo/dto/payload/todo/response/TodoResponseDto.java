package org.sd.todo.dto.payload.todo.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TodoResponseDto {
    private final Long id;
    private final String title;
    private final Date dueDate;
    private final String status;

    public TodoResponseDto(Long id, String title, Date dueDate, String status) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}

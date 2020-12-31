package org.sd.todo.dto.payload.todo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.sd.todo.validator.constraints.CorrectTodoStatusConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class TodoRequestDto {

    @NotBlank
    @Size(min = 5, max = 250)
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @CorrectTodoStatusConstraint
    private String status;

    public TodoRequestDto(String title, Date dueDate, String status) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

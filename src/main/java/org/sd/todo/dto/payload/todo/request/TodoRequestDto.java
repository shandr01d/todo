package org.sd.todo.dto.payload.todo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.sd.todo.validator.constraints.CorrectTodoStatusConstraint;
import org.sd.todo.validator.constraints.DateFormatConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoRequestDto {

    public interface ValidationCreate { }

    public interface ValidationUpdate { }

    @NotBlank(message = "title is required", groups = {ValidationCreate.class})
    @Size(min = 3, max = 250, message = "should be at least 3 chars and less then 250 chars", groups = {ValidationCreate.class, ValidationUpdate.class})
    private String title;

//    @DateFormatConstraint(groups = {ValidationCreate.class, ValidationUpdate.class})
//    private final String strDueDate;

    @NotNull(groups = {ValidationCreate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @NotNull(groups = {ValidationCreate.class})
    @CorrectTodoStatusConstraint(groups = {ValidationCreate.class, ValidationUpdate.class})
    private String status;

    public TodoRequestDto(String title, Date dueDate, String status) throws ParseException {
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

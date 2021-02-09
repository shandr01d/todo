package org.sd.todo.services.dataTransformer;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.dto.payload.todo.response.TodoResponseDto;
import org.sd.todo.entity.Todo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Primary
@Service
public class TodoTransformerImpl implements TodoTransformer {

    @Override
    public TodoResponseDto transformToResponseDto(Todo todo) {
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getTodosRecord().getDueDate(),
                todo.getStatus().toString()
        );
    }

    @Override
    public TodoDto transformFromRequestDto(TodoRequestDto todoRequestDto) {
        TodoDto todoDto = new TodoDto();

        if(todoRequestDto.getDueDate() == null){
            LocalDate now = LocalDate.now();
            todoRequestDto.setDueDate(Date.from(now
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                    )
            );
        }
        todoDto.setTitle(todoRequestDto.getTitle());
        todoDto.setDueDate(todoRequestDto.getDueDate());
        todoDto.setStatus(todoRequestDto.getStatus());
        return todoDto;
    }
}

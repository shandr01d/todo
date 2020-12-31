package org.sd.todo.services.dataTransformer;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.dto.payload.todo.response.TodoResponseDto;
import org.sd.todo.entity.Todo;

public interface TodoTransformer {
    public TodoResponseDto transformToResponseDto(Todo todo);
    public TodoDto transformFromRequestDto(TodoRequestDto todoRequestDto);
}

package org.sd.todo.services.todo;

import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.dto.payload.todo.response.TodoResponseDto;

import java.util.List;

public interface TodoServiceFacade {
    public List<TodoResponseDto> list(TodoRequestDto todoRequestDto);
    public TodoResponseDto create(TodoRequestDto todoRequestDto);
    public TodoResponseDto get(Long id);
    public TodoResponseDto update(TodoRequestDto todoRequestDto, Long id);
    public void delete(Long id);
}

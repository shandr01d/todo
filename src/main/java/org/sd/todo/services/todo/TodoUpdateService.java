package org.sd.todo.services.todo;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.entity.Todo;

public interface TodoUpdateService {
    public Todo update(Todo todo, TodoDto todoDto);
    public Todo update(TodoRequestDto todoDto, Long id);
}

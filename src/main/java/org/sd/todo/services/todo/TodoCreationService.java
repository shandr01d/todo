package org.sd.todo.services.todo;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.Todo;

public interface TodoCreationService {
    public Todo create(TodoDto todoDto);
}

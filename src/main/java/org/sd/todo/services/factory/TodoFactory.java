package org.sd.todo.services.factory;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.TodosRecord;
import org.sd.todo.entity.Todo;

public interface TodoFactory {
    public Todo create(TodoDto todoDto, TodosRecord todosRecord);
}

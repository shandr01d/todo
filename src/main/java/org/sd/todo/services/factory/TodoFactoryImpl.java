package org.sd.todo.services.factory;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.TodosRecord;
import org.sd.todo.entity.Todo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TodoFactoryImpl implements TodoFactory {

    public Todo create(TodoDto todoDto, TodosRecord todosRecord){
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setTodosRecord(todosRecord);
        todo.setStatus(Todo.Status.valueOf(todoDto.getStatus()));
        
        return todo;
    }
}

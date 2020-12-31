package org.sd.todo.services.factory;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.List;
import org.sd.todo.entity.Todo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TodoFactoryImpl implements TodoFactory {

    public Todo create(TodoDto todoDto, List list){
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setList(list);
        todo.setStatus(Todo.Type.valueOf(todoDto.getStatus()));
        
        return todo;
    }
}

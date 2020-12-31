package org.sd.todo.services.todo;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.entity.List;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.ListRepository;
import org.sd.todo.repository.TodoRepository;
import org.sd.todo.services.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.function.Consumer;

@Primary
@Service
public class TodoUpdateServiceImpl implements TodoUpdateService {

    private final TodoRepository todoRepository;
    private final ListRepository listRepository;

    @Autowired
    public TodoUpdateServiceImpl(TodoRepository todoRepository, ListRepository listRepository) {
        this.todoRepository = todoRepository;
        this.listRepository = listRepository;
    }

    @Override
    public Todo update(Todo todo, TodoDto todoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userPrincipal.getUser();

        if(todoDto.getDueDate() != null){
            List list = listRepository.findOneByDueDateAndOwnerOrCreate(todoDto.getDueDate(), owner);
            todo.setList(list);
        }

        todo.setTitle(todoDto.getTitle());
        todo.setStatus(Todo.Type.valueOf(todoDto.getStatus()));

        return todoRepository.save(todo);
    }

    @Override
    public Todo update(TodoRequestDto todoRequestDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        TodoDto todoDto = new TodoDto();
        setIfNotNull(todoDto::setDueDate, todoRequestDto.getDueDate(), todo.getList().getDueDate());
        setIfNotNull(todoDto::setTitle, todoRequestDto.getTitle(), todo.getTitle());
        setIfNotNull(todoDto::setStatus, todoRequestDto.getStatus(), todo.getStatus().toString());

        return update(todo, todoDto);
    }

    private <T> void setIfNotNull(final Consumer<T> setter, final T value, final T defaultValue) {
        if (value != null) {
            setter.accept(value);
        } else {
            setter.accept(defaultValue);
        }
    }
}

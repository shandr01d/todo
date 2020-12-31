package org.sd.todo.services.todo;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.entity.List;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.ListRepository;
import org.sd.todo.repository.TodoRepository;
import org.sd.todo.services.factory.TodoFactory;
import org.sd.todo.services.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class TodoCreationServiceImpl implements TodoCreationService {

    private final TodoFactory todoFactory;
    private final ListRepository listRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public TodoCreationServiceImpl(TodoFactory todoFactory, ListRepository listRepository, TodoRepository todoRepository) {
        this.todoFactory = todoFactory;
        this.listRepository = listRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo create(TodoDto todoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userPrincipal.getUser();

        List list = listRepository.findOneByDueDateAndOwnerOrCreate(todoDto.getDueDate(), owner);
        return todoRepository.save(todoFactory.create(todoDto, list));
    }
}

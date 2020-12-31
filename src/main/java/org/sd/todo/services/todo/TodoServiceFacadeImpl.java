package org.sd.todo.services.todo;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.dto.payload.todo.response.TodoResponseDto;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.TodoRepository;
import org.sd.todo.services.dataTransformer.TodoTransformer;
import org.sd.todo.services.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
public class TodoServiceFacadeImpl implements TodoServiceFacade {

    private final TodoRepository todoRepository;
    private final TodoTransformer todoTransformer;
    private final TodoCreationService todoCreationService;
    private final TodoUpdateService todoUpdateService;

    @Autowired
    public TodoServiceFacadeImpl(
            TodoTransformer todoTransformer,
            TodoRepository todoRepository,
            TodoCreationService todoCreationService,
            TodoUpdateService todoUpdateService
    ) {
        this.todoTransformer = todoTransformer;
        this.todoRepository = todoRepository;
        this.todoCreationService = todoCreationService;
        this.todoUpdateService = todoUpdateService;
    }

    @Override
    public List<TodoResponseDto> list(TodoRequestDto todoRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userPrincipal.getUser();

        TodoDto todoDto = todoTransformer.transformFromRequestDto(todoRequestDto);
        List<Todo> todos = todoRepository.findAllByOwnerAndCriteria(owner, todoDto);
        List<TodoResponseDto> todoResponseDTOs = new ArrayList<>();
        todos.forEach(todo -> {
            todoResponseDTOs.add(
                    new TodoResponseDto(
                            todo.getId(),
                            todo.getTitle(),
                            todo.getList().getDueDate(),
                            todo.getStatus().name()
                    )
            );
        });
        return todoResponseDTOs;
    }

    public TodoResponseDto create(TodoRequestDto todoRequestDto) {
        TodoDto todoDto = todoTransformer.transformFromRequestDto(todoRequestDto);
        Todo todo = todoCreationService.create(todoDto);
        return todoTransformer.transformToResponseDto(todo);
    }

    @Override
    public TodoResponseDto get(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return todoTransformer.transformToResponseDto(todo);
    }

    @Override
    public TodoResponseDto update(TodoRequestDto todoRequestDto, Long id) {
        Todo todo = todoUpdateService.update(todoRequestDto, id);
        return todoTransformer.transformToResponseDto(todo);
    }
}

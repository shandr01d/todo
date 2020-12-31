package org.sd.todo.controller;

import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.repository.TodoRepository;
import org.sd.todo.services.todo.TodoServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;
    private final TodoServiceFacade todoServiceFacade;

    @Autowired
    public TodoController(
            TodoRepository todoRepository,
            TodoServiceFacade todoServiceFacade
    ) {
        this.todoRepository = todoRepository;
        this.todoServiceFacade = todoServiceFacade;
    }

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<?> list(@Valid TodoRequestDto todoRequestDto) {
        return ResponseEntity.ok(
                todoServiceFacade.list(todoRequestDto)
        );
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> create(@Valid @RequestBody TodoRequestDto todoRequestDto) {
        return ResponseEntity.ok(
                todoServiceFacade.create(todoRequestDto)
        );
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                todoServiceFacade.get(id)
        );
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> update(@Valid @RequestBody TodoRequestDto todoRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(
                todoServiceFacade.update(todoRequestDto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

}
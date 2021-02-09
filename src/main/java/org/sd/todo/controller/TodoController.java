package org.sd.todo.controller;

import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.services.todo.TodoServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping(path="/api/todos")
public class TodoController {

    private final TodoServiceFacade todoServiceFacade;

    @Autowired
    public TodoController(TodoServiceFacade todoServiceFacade) {
        this.todoServiceFacade = todoServiceFacade;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> list(TodoRequestDto todoRequestDto) {
        return ResponseEntity.ok(
                todoServiceFacade.list(todoRequestDto)
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> create(
            @RequestBody @Validated(TodoRequestDto.ValidationCreate.class) TodoRequestDto todoRequestDto
    ) throws Exception {
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

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public @ResponseBody ResponseEntity<?> update (
            @RequestBody @Validated(TodoRequestDto.ValidationUpdate.class) TodoRequestDto todoRequestDto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                todoServiceFacade.update(todoRequestDto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoServiceFacade.delete(id);
    }

}
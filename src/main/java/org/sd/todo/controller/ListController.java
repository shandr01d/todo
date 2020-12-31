package org.sd.todo.controller;

import org.sd.todo.dto.ListDto;
import org.sd.todo.entity.List;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.ListRepository;
import org.sd.todo.repository.UserRepository;
import org.sd.todo.services.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@RestController
@RequestMapping(path="/api/lists")
public class ListController {

    @Autowired
    private ListRepository listRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path= "/")
    public @ResponseBody Iterable<List> list() {
        return listRepository.findAll();
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public @ResponseBody List create(@RequestBody ListDto listDto) throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userPrincipal.getUser();

        List list = new List();
        list.setDueDate(listDto.getDueDate());
        list.setOwner(owner);
        listRepository.save(list);
        return list;
    }

    @GetMapping("/{id}")
    List one(@PathVariable int id) {
        List list = listRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("yep! new title");
        todo.setStatus(Todo.Type.CREATED);
        todo.setList(list);

        ArrayList<Todo> todos = new ArrayList<>();
        todos.add(todo);
        list.setTodos(todos);

        return list;
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    List replaceEmployee(@RequestBody ListDto listDto, @PathVariable Integer id) {
        return listRepository.findById(id)
                .map(foundList -> {
                    foundList.setDueDate(listDto.getDueDate());
                    return listRepository.save(foundList);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        listRepository.deleteById(id);
    }

}
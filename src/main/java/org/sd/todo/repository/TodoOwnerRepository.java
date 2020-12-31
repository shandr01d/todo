package org.sd.todo.repository;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;

import java.util.List;

public interface TodoOwnerRepository {
    public List<Todo> findAllByOwnerAndCriteria(User owner, TodoDto todoDto);
}

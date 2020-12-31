package org.sd.todo.repository;

import org.sd.todo.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long>, TodoOwnerRepository {

}

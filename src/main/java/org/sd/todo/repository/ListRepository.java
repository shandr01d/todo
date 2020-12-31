package org.sd.todo.repository;

import org.sd.todo.entity.List;
import org.sd.todo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Integer>, ListFindOrCreateRepository {
    public Optional<List> findOneByDueDateAndOwner(Date dueDate, User owner);
}

package org.sd.todo.repository;

import org.sd.todo.entity.TodosRecord;
import org.sd.todo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface TodosRecordRepository extends CrudRepository<TodosRecord, Integer>, TodosRecordFindOrCreateRepository {
    public Optional<TodosRecord> findOneByDueDateAndOwner(Date dueDate, User owner);
}

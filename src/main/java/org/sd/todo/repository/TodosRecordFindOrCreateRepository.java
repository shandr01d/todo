package org.sd.todo.repository;

import org.sd.todo.entity.TodosRecord;
import org.sd.todo.entity.User;

import java.util.Date;

public interface TodosRecordFindOrCreateRepository {
    public TodosRecord findOneByDueDateAndOwnerOrCreate(Date dueDate, User owner);
}

package org.sd.todo.repository;

import org.sd.todo.entity.List;
import org.sd.todo.entity.User;

import java.util.Date;

public interface ListFindOrCreateRepository {
    public List findOneByDueDateAndOwnerOrCreate(Date dueDate, User owner);
}

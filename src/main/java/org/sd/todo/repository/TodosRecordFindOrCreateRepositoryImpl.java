package org.sd.todo.repository;

import org.sd.todo.entity.TodosRecord;
import org.sd.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

public class TodosRecordFindOrCreateRepositoryImpl implements TodosRecordFindOrCreateRepository {

    private final TodosRecordRepository todosRecordRepository;

    @Autowired
    public TodosRecordFindOrCreateRepositoryImpl(TodosRecordRepository todosRecordRepository) {
        this.todosRecordRepository = todosRecordRepository;
    }

    @Override
    public TodosRecord findOneByDueDateAndOwnerOrCreate(Date dueDate, User owner) {
        Optional<TodosRecord> todosRecord = todosRecordRepository.findOneByDueDateAndOwner(dueDate, owner);
        if(todosRecord.isEmpty()){
            TodosRecord newTodosRecord = new TodosRecord();
            newTodosRecord.setDueDate(dueDate);
            newTodosRecord.setOwner(owner);
            return todosRecordRepository.save(newTodosRecord);
        }

        return todosRecord.get();
    }
}

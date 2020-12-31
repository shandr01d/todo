package org.sd.todo.repository;

import org.sd.todo.entity.List;
import org.sd.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

public class ListFindOrCreateRepositoryImpl implements ListFindOrCreateRepository {

    private final ListRepository listRepository;

    @Autowired
    public ListFindOrCreateRepositoryImpl(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Override
    public List findOneByDueDateAndOwnerOrCreate(Date dueDate, User owner) {
        Optional<List> list = listRepository.findOneByDueDateAndOwner(dueDate, owner);
        if(list.isEmpty()){
            List newList = new List();
            newList.setDueDate(dueDate);
            newList.setOwner(owner);
            return listRepository.save(newList);
        }

        return list.get();
    }
}

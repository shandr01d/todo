package org.sd.todo.repository;

import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<Todo, Long>, TodoOwnerRepository {

    @Query("select t from Todo t inner join TodosRecord tr on t.todosRecord = tr.id where t.status=?1 and tr.dueDate=?2")
    public List<Todo> findByStatusAndDueDate(Todo.Status status, Date dueDate);

    @Query("select t from Todo t inner join TodosRecord tr on t.todosRecord = tr.id where t.status=?1 and tr.owner=?2")
    public List<Todo> findByStatusAndOwner(Todo.Status status, User owner);

    @Query("select t from Todo t inner join TodosRecord tr on t.todosRecord = tr.id where t.id=?1 and tr.owner=?2")
    public Optional<Todo> findTopByIdAndOwner(Long id, User owner);
}

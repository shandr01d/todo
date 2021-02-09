package org.sd.todo.repository;

import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value=
            "select distinct users.* from users " +
            "inner join todos_records tr on users.id = tr.owner_id " +
            "inner join todos t on t.todos_record_id = tr.id " +
            "where t.status=:#{#status.name()} and tr.due_date=:dueDate",
            nativeQuery=true)
    public List<User> findByTodoStatusAndListDueDate(@Param("status") Todo.Status status, @Param("dueDate") Date dueDate);
}

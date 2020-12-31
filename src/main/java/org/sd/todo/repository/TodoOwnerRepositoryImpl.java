package org.sd.todo.repository;

import org.sd.todo.dto.TodoDto;
import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TodoOwnerRepositoryImpl implements TodoOwnerRepository {

    @Autowired
    EntityManager em;

    @Override
    public List<Todo> findAllByOwnerAndCriteria(User owner, TodoDto todoDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Todo> cq = cb.createQuery(Todo.class);

        Root<Todo> todo = cq.from(Todo.class);
        Predicate ownerPredicate = cb.equal(todo.get("list").get("owner"), owner.getId());
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(ownerPredicate);

        if(todoDto.getDueDate() != null){
            Predicate dueDatePredicate = cb.equal(todo.get("list").get("dueDate"), todoDto.getDueDate());
            predicates.add(dueDatePredicate);
        }
        if(todoDto.getTitle() != null){
            Predicate titlePredicate = cb.like(todo.get("title"), "%" + todoDto.getTitle() + "%");
            predicates.add(titlePredicate);
        }
        if(todoDto.getStatus() != null){
            Predicate statusPredicate = cb.like(todo.get("status"), todoDto.getStatus());
            predicates.add(statusPredicate);
        }

        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Todo> query = em.createQuery(cq);
        return query.getResultList();
    }
}

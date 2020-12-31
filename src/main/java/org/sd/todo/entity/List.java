package org.sd.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "lists")
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date dueDate;

    @JsonManagedReference
    @OneToMany(targetEntity=Todo.class, mappedBy="list", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Todo> todos = new ArrayList<>();

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="owner_id")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Collection<Todo> getTodos() {
        return todos;
    }

    public void setTodos(Collection<Todo> todos) {
        this.todos = todos;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}

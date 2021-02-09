package org.sd.todo.messaging;

import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.TodoRepository;
import org.sd.todo.repository.UserRepository;
import org.sd.todo.services.notifiers.email.EmailService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UncompletedTodoConsumer {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final EmailService emailService;

    @Autowired
    public UncompletedTodoConsumer(UserRepository userRepository, TodoRepository todoRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.emailService = emailService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.template.todo.queue}", durable = "true"),
            exchange = @Exchange(value = "${spring.rabbitmq.template.todo.exchange}", type = "topic"),
            key = UncompletedTodoProducer.ROUTING_KEY
        )
    )
    public void consume(Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if(result.isEmpty()){
            return;
        }

        User user = result.get();

        List<Todo> todos = todoRepository.findByStatusAndOwner(Todo.Status.CREATED, user);
        todos.forEach(todo -> {
            todo.setStatus(Todo.Status.FAILED);
        });
        todoRepository.saveAll(todos);

        emailService.sendSimpleMessage(
                "admin@todo.com",
                user.getEmail(),
                "Uncompleted todos",
                "Hi! " + user.getUsername() + "! Your todos were closed!");
    }
}
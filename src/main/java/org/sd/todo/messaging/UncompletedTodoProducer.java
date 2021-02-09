package org.sd.todo.messaging;

import org.sd.todo.entity.Todo;
import org.sd.todo.entity.User;
import org.sd.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
public class UncompletedTodoProducer {

    public static final String ROUTING_KEY = "todo.uncompleted";

    private final UserRepository userRepository;
    private final TodoNotifyingService todoNotifyingService;

    @Autowired
    public UncompletedTodoProducer(UserRepository userRepository, TodoNotifyingService todoNotifyingService) {
        this.userRepository = userRepository;
        this.todoNotifyingService = todoNotifyingService;
    }

    @Scheduled(fixedDelay = 100000)
    public void sendNotifications()
    {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        java.util.Date date = java.sql.Date.valueOf(yesterday);
        List<User> users = userRepository.findByTodoStatusAndListDueDate(Todo.Status.CREATED, date);
        users.forEach(user -> {
            todoNotifyingService.send(ROUTING_KEY, user.getId());
        });
    }
}

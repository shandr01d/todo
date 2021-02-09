package org.sd.todo.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Primary
@Service
public class TodoNotifyingServiceImpl implements TodoNotifyingService {

    @Autowired
    private TopicExchange topic;
    private final RabbitTemplate rabbit;

    @Autowired
    public TodoNotifyingServiceImpl(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void send(String routingKey, Long userId) {
        rabbit.convertAndSend(topic.getName(), routingKey, userId);
    }

}
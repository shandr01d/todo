package org.sd.todo.messaging;

public interface TodoNotifyingService {
    public void send(String routingKey, Long todoId);
}
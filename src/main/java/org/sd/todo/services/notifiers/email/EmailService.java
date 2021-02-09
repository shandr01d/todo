package org.sd.todo.services.notifiers.email;

public interface EmailService {
    public void sendSimpleMessage(String from, String to, String subject, String text);
}

package org.sd.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserCreationException extends ResponseStatusException {
    public UserCreationException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}

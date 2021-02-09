package org.sd.todo.exceptions;

import org.hibernate.PropertyValueException;
import org.sd.todo.dto.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo handleEntityNotFound(EntityNotFoundException exception, WebRequest request) {
        return new ErrorInfo(HttpStatus.NOT_FOUND, extractUriFromWebRequest(request), "Not found", exception);
    }

    @ExceptionHandler({PropertyValueException.class, UserCreationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleValidationExceptions(Exception exception, WebRequest request) {
        return new ErrorInfo(HttpStatus.BAD_REQUEST, extractUriFromWebRequest(request), "Bad request", exception);
    }

    private String extractUriFromWebRequest(WebRequest request){
        return ((ServletWebRequest)request).getRequest().getRequestURI().toString();
    }
}

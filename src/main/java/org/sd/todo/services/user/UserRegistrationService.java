package org.sd.todo.services.user;

import org.sd.todo.dto.payload.auth.request.RegisterRequestDto;
import org.sd.todo.dto.payload.auth.response.RegisterResponseDto;
import org.sd.todo.exceptions.UserCreationException;

public interface UserRegistrationService {
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) throws UserCreationException, Exception;
}

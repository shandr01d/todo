package org.sd.todo.services.user;

import org.sd.todo.dto.payload.auth.response.LoginResponseDto;

public interface UserLoginService {
    public LoginResponseDto signIn(String username, String password);
}

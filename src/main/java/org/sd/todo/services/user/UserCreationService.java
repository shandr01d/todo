package org.sd.todo.services.user;

import org.sd.todo.dto.UserDto;
import org.sd.todo.entity.User;
import org.sd.todo.exceptions.UserCreationException;

public interface UserCreationService {
    User create(UserDto userDto) throws UserCreationException;
}

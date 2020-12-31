package org.sd.todo.services.factory;

import org.sd.todo.dto.UserDto;
import org.sd.todo.entity.User;

public interface UserFactory {
    public User create(UserDto userDto);
}

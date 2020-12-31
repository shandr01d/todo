package org.sd.todo.services.factory;

import org.sd.todo.dto.UserDto;
import org.sd.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Primary
@Component
public class UserFactoryImpl implements UserFactory {

    private final PasswordEncoder encoder;

    @Autowired
    public UserFactoryImpl(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    public User create(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPlainPassword()));
        user.setRoles(userDto.getRoles());

        return user;
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }
}

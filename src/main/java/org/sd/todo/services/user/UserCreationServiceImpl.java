package org.sd.todo.services.user;

import org.sd.todo.dto.UserDto;
import org.sd.todo.entity.Role;
import org.sd.todo.entity.User;
import org.sd.todo.exceptions.UserCreationException;
import org.sd.todo.repository.RoleRepository;
import org.sd.todo.repository.UserRepository;
import org.sd.todo.services.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@Primary
public class UserCreationServiceImpl implements UserCreationService {

    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserCreationServiceImpl(UserFactory userFactory, UserRepository userRepository, RoleRepository roleRepository){
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(UserDto userDto) throws UserCreationException {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserCreationException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserCreationException("Error: Email is already taken!");
        }

        Collection<Role> roles = new HashSet<>();

        if(userDto.getRoles().isEmpty()){
            Role userRole = roleRepository.findByName(Role.Type.ROLE_USER)
                    .orElseThrow(() -> new UserCreationException("Error: Role is not found."));
            roles.add(userRole);
            userDto.setRoles(roles);
        }

        User user = userFactory.create(userDto);
        return userRepository.save(user);
    }
}

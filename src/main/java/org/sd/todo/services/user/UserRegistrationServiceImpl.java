package org.sd.todo.services.user;

import org.sd.todo.dto.UserDto;
import org.sd.todo.dto.payload.auth.request.RegisterRequestDto;
import org.sd.todo.dto.payload.auth.response.RegisterResponseDto;
import org.sd.todo.entity.Role;
import org.sd.todo.entity.User;
import org.sd.todo.exceptions.UserCreationException;
import org.sd.todo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Primary
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserCreationService userCreationService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRegistrationServiceImpl(UserCreationService userCreationService, RoleRepository roleRepository){
        this.userCreationService = userCreationService;
        this.roleRepository = roleRepository;
    }

    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) throws UserCreationException, Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername(registerRequestDto.getUsername());
        userDto.setEmail(registerRequestDto.getEmail());
        userDto.setPlainPassword(registerRequestDto.getPassword());

        Collection<String> roleString = registerRequestDto.getRoles();

        roleString.forEach(
            role -> {
                Role foundRole = roleRepository.findByName(Role.Type.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                userDto.addRole(foundRole);
            }
        );

        User user = userCreationService.create(userDto);
        return new RegisterResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}

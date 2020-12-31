package org.sd.todo.services.user;

import org.sd.todo.dto.payload.auth.response.LoginResponseDto;
import org.sd.todo.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class UserLoginServiceImpl implements UserLoginService {

    AuthenticationManager authenticationManager;
    JWTUtils jwtUtils;

    @Autowired
    public UserLoginServiceImpl(AuthenticationManager authenticationManager, JWTUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDto signIn(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponseDto(
                    jwt,
                    userDetails.getUser().getId(),
                    userDetails.getUsername(),
                    userDetails.getUser().getEmail(),
                    roles
                );
    }
}

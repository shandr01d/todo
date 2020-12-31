package org.sd.todo.controller;

import org.sd.todo.dto.payload.auth.request.LoginRequestDto;
import org.sd.todo.dto.payload.auth.request.RegisterRequestDto;
import org.sd.todo.exceptions.UserCreationException;
import org.sd.todo.services.user.UserLoginService;
import org.sd.todo.services.user.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserLoginService loginService;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public AuthController(UserLoginService loginService, UserRegistrationService userRegistrationService){
        this.loginService = loginService;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(
            this.loginService.signIn(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
    }

    @PostMapping("/sign-up")
    public @ResponseBody ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto)
            throws UserCreationException, Exception {
        return ResponseEntity.ok(
                this.userRegistrationService.registerUser(registerRequestDto)
        );
    }
}

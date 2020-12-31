package org.sd.todo.dto.payload.auth.response;

public class RegisterResponseDto {

    private Long id;
    private String username;
    private String email;

    public RegisterResponseDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

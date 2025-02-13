package org.sd.todo.dto.payload.auth.request;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

public class RegisterRequestDto {
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
 
    @NotNull
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private Set<String> roles;
    
    @NotNull
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Collection<String> getRoles() {
      return this.roles;
    }
    
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

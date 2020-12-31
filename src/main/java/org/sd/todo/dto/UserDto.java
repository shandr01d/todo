package org.sd.todo.dto;

import org.sd.todo.entity.Role;

import java.util.ArrayList;
import java.util.Collection;

public class UserDto {
    private String username;
    private String email;
    private String plainPassword;
    private Collection<Role> roles = new ArrayList<Role>();

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

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if(!roles.contains(role)){
            this.roles.add(role);
        }
    }
}

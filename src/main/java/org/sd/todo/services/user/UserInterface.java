package org.sd.todo.services.user;

import org.sd.todo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInterface extends UserDetails {
    User getUser();
}
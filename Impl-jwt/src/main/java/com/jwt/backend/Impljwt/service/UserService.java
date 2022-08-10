package com.jwt.backend.Impljwt.service;

import com.jwt.backend.Impljwt.domain.Role;
import com.jwt.backend.Impljwt.domain.User;
import java.util.List;


public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}

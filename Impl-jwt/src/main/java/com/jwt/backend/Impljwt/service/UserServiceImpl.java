package com.jwt.backend.Impljwt.service;

import com.jwt.backend.Impljwt.Repository.RoleRepository;
import com.jwt.backend.Impljwt.Repository.UserRepository;
import com.jwt.backend.Impljwt.domain.Role;
import com.jwt.backend.Impljwt.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository UR;
    private final RoleRepository RR;
    
    
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
        @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UR.findByUsername(username);
        if (user == null) {
            log.error("user not found in database");
            throw new UsernameNotFoundException("user not found in database");
        } else {
            log.info("user found in database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    @Override
    public User saveUser(User user) {
        log.info("saving a new userto the database " + user.getName());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return UR.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving a new role the database " + role.getName());
        return RR.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding a new role to a user");
        User user = UR.findByUsername(username);
        Role role = RR.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching a specific user with the name :" + username);
        return UR.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching the list of users");
        return UR.findAll();
    }



}   

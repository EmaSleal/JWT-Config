package com.jwt.backend.Impljwt;

import com.jwt.backend.Impljwt.domain.Role;
import com.jwt.backend.Impljwt.domain.User;
import com.jwt.backend.Impljwt.service.UserService;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ImplJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplJwtApplication.class, args);
    }
    
 
 
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null,"ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "ema sl", "m4n0", "12345", new ArrayList<>()));
            userService.saveUser(new User(null, "adrian alvarado", "alvarado", "7410", new ArrayList<>()));
            userService.saveUser(new User(null, "juan hurtado", "juan", "8520", new ArrayList<>()));
            userService.saveUser(new User(null, "hernan sanchez", "hernan", "9630", new ArrayList<>()));
            userService.saveUser(new User(null, "silvio solano", "silvio", "1230", new ArrayList<>()));

            userService.addRoleToUser("m4n0", "ROLE_USER");
            userService.addRoleToUser("alvarado", "ROLE_USER");
            userService.addRoleToUser("alvarado", "ROLE_ADMIN");
            userService.addRoleToUser("hernan", "ROLE_USER");
            userService.addRoleToUser("juan", "ROLE_MANAGER");
            userService.addRoleToUser("silvio", "ROLE_ADMIN");
            userService.addRoleToUser("silvio", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("silvio", "ROLE_MANAGER");

        };
    }
    
    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    
}

package com.jwt.backend.Impljwt.Repository;

import com.jwt.backend.Impljwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

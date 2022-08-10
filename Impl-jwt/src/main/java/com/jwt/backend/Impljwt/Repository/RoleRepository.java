
package com.jwt.backend.Impljwt.Repository;

import com.jwt.backend.Impljwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
} 
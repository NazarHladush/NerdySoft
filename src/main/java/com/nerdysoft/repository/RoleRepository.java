package com.nerdysoft.repository;

import com.nerdysoft.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String name);
}

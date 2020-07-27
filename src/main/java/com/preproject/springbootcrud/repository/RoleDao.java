package com.preproject.springbootcrud.repository;

import com.preproject.springbootcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

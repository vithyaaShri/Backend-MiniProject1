package com.project.miniproject1.repository;


import com.project.miniproject1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleSecurityRepo extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

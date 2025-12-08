package com.stevenjtk.trivia.repository;

import com.stevenjtk.trivia.entity.Roles;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    Roles findById(Integer id);
    Roles findByRoleName(String roleName);
}

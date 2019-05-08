package com.development.ordering.repository;

import com.development.ordering.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
    public UserRole findByUsersId(Long id);
    public UserRole findByUsersUsername(String username);
}

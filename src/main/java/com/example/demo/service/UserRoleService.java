package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface UserRoleService {
    void createUserRole(User user, Role role);
}

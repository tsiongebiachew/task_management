package com.organization.taskdirectory.service;

import com.organization.taskdirectory.entity.User;
import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User findById(Long id); // ✅ Add this
    List<User> getAllUsers();
}

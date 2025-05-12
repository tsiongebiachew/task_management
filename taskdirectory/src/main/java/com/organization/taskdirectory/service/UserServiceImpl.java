package com.organization.taskdirectory.service;



import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null); // ✅ Add this
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

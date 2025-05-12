package com.organization.taskdirectory.service;

import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.User;

import java.util.List;

public interface TaskService {
    List<Task> getTasksByAssignedUser(User user);

    Task findById(Long taskId);

    void save(Task task);
}


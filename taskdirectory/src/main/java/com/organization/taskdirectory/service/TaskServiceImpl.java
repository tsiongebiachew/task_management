package com.organization.taskdirectory.service;

import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getTasksByAssignedUser(User user) {
        return taskRepository.findByAssignedTo(user);
    }

    @Override
    public Task findById(Long taskId) {
        // Find the task by its ID from the repository.
        Optional<Task> task = taskRepository.findById(taskId);
        // Return the task if found, or throw an exception if not found.
        return task.orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
    }

    @Override
    public void save(Task task) {
        // Save or update the task in the repository.
        taskRepository.save(task);
    }

    
}

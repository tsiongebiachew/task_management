package com.organization.taskdirectory.repository;

import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(User user);
}

package com.organization.taskdirectory.controller;

import com.organization.taskdirectory.entity.Role;
import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.TaskStatus;
import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.repository.TaskRepository;
import com.organization.taskdirectory.repository.UserRepository;
import com.organization.taskdirectory.service.TaskService;
import com.organization.taskdirectory.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/supervisor")  // base path
@RequiredArgsConstructor
public class SupervisorController {

    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final UserRepository userRepo;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "supervisor/dashboard";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "supervisor/tasks";
    }

    @GetMapping("/tasks/new")
    public String assignNewTask(Model model) {
        // Only show users who are not ADMIN or SUPERVISOR
         List<User> assignableUsers = userRepo.findAll().stream()
            .filter(user -> !user.getRole().equals(Role.ADMINISTRATOR) && !user.getRole().equals(Role.SUPERVISOR))
            .collect(Collectors.toList());

        model.addAttribute("task", new Task());
    model.addAttribute("users", assignableUsers);  // 
        return "supervisor/create_task";
    }

     



    @PostMapping("/tasks/new")
    public String saveNewTask(@ModelAttribute Task task, @RequestParam Long assignedToUserId) {
        User assignedUser = userService.findById(assignedToUserId);
        task.setAssignedTo(assignedUser);
        task.setStatus(TaskStatus.PENDING);
        taskService.save(task);
        return "redirect:/supervisor/tasks";
    }

    @GetMapping("/tasks/{taskId}")
    public String viewTaskDetails(@PathVariable  Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        model.addAttribute("task", task);
        return "supervisor/task_details";
    }

    @PostMapping("/tasks/review")
    public String markTaskAsReviewed(@RequestParam Long taskId) {
        Task task = taskService.findById(taskId);
        task.setStatus(TaskStatus.SUPERVISOR_REVIEWED);
        taskService.save(task);
        return "redirect:/supervisor/tasks";
    }
}

package com.organization.taskdirectory.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.entity.Role;
import java.util.stream.Collectors;
import java.util.List;


import com.organization.taskdirectory.entity.TaskStatus;
import com.organization.taskdirectory.repository.TaskRepository;
import com.organization.taskdirectory.repository.UserRepository;
@Controller
public class AdminController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public AdminController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        return "admin/tasks";
    }

   @GetMapping("/admin/tasks/new")
public String createTaskForm(Model model) {
    // Filter out ADMIN and SUPERVISOR users
    List<User> assignableUsers = userRepo.findAll().stream()
            .filter(user -> !user.getRole().equals(Role.ADMINISTRATOR) && !user.getRole().equals(Role.SUPERVISOR))
            .collect(Collectors.toList());
    
    model.addAttribute("task", new Task());
    model.addAttribute("users", assignableUsers);  // Pass the filtered list
    return "admin/create-task";
}
 @PostMapping("/admin/tasks/review")
public String markTaskReviewed(@RequestParam Long taskId) {
    Task task = taskRepo.findById(taskId).orElseThrow();
    task.setStatus(TaskStatus.ADMIN_REVIEWED);
    taskRepo.save(task);
    return "redirect:/admin/tasks";
}
@GetMapping("/admin/tasks/edit")
public String editTask(@RequestParam Long id, Model model) {
    Task task = taskRepo.findById(id).orElseThrow();
    model.addAttribute("task", task);
    model.addAttribute("users", userRepo.findAll());
    return "admin/edit-task";
}

@PostMapping("/admin/tasks/delete")
public String deleteTask(@RequestParam Long taskId) {
    taskRepo.deleteById(taskId);
    return "redirect:/admin/tasks";
}
@PostMapping("/admin/tasks/update")
public String updateTask(@ModelAttribute Task task, @RequestParam Long assignedToId) {
    User user = userRepo.findById(assignedToId).orElseThrow();
    task.setAssignedTo(user);
    task.setCreatedAt(LocalDateTime.now());
    taskRepo.save(task);
    return "redirect:/admin/tasks";
}
@GetMapping("/admin/tasks/{taskId}")
public String viewTaskDetails(@PathVariable("taskId") Long taskId, Model model) {
    Task task = taskRepo.findById(taskId).orElseThrow();
    model.addAttribute("task", task);
    return "admin/task_detail"; // reuse supervisor's detail template
}




    @PostMapping("/admin/tasks/save")
    public String saveTask(@ModelAttribute Task task, @RequestParam Long assignedToId) {
        User user = userRepo.findById(assignedToId).orElseThrow();
        task.setAssignedTo(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.ASSIGNED);
        taskRepo.save(task);
        return "redirect:/admin/tasks";
    }
}

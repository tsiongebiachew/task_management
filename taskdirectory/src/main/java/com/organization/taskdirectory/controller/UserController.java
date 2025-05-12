package com.organization.taskdirectory.controller;

import com.organization.taskdirectory.entity.Task;
import com.organization.taskdirectory.entity.TaskStatus;
import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.service.TaskService;
import com.organization.taskdirectory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Task> tasks = taskService.getTasksByAssignedUser(user);
        model.addAttribute("tasks", tasks);
        return "user/dashboard";  // <-- matches templates/user/dashboard.html
    }

    @GetMapping("/user/tasks")
    public String viewUserTasks(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Task> tasks = taskService.getTasksByAssignedUser(user);
        model.addAttribute("tasks", tasks);
        return "user/user_task_list";  // <-- matches templates/user/user_task_list.html
    }

 @GetMapping("/user/tasks/edit/{taskId}")
public String editTask(@PathVariable Long taskId, Model model) {
    System.out.println("Editing task with ID: " + taskId);
    Task task = taskService.findById(taskId);
    model.addAttribute("task", task);
    return "user/update";
}

@PostMapping("/user/tasks/update")
public String updateTask(@RequestParam Long taskId,
                         @RequestParam String status,
                         @RequestParam(required = false) MultipartFile file) throws IOException {

    Task task = taskService.findById(taskId);
    task.setStatus(TaskStatus.valueOf(status));

    if (file != null && !file.isEmpty()) {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads", filename);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        task.setDocumentPath(filename);  // Save path to DB
    }

    taskService.save(task);

    return "redirect:/user/dashboard";
}


}

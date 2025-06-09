package org.akash.task.controller;

import org.akash.task.model.Task;
import org.akash.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String taskId) {
        if (taskId != null) {
            return service.getTaskById(taskId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(service.getAllTasks());
    }

    @PutMapping
    public ResponseEntity<?> putTask(@RequestBody Task task) {
        try {
            return ResponseEntity.ok(service.saveTask(task));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        service.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        List<Task> tasks = service.searchTasksByName(name);
        if (tasks.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.executeTask(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Execution failed: " + e.getMessage());
        }
    }
}

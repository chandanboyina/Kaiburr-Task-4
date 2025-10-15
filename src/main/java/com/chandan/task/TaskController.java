package com.chandan.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name) {

        if (id != null) {
            return taskService.getTaskById(id)
                    .map(task -> ResponseEntity.ok(List.of(task)))
                    .orElse(ResponseEntity.notFound().build());
        } else if (name != null) {
            List<Task> tasks = taskService.findTasksByName(name);
            if (tasks.isEmpty()) {
                // Return 404 if no tasks are found.
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tasks);
        } else {
            // Return all tasks if no parameters are passed.
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        }
    }

    @PutMapping
    public ResponseEntity<Task> putTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.putTask(task);
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<Task> putTaskExecution(@PathVariable String id) {
        return taskService.putTaskExecution(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

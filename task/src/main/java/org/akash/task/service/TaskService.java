package org.akash.task.service;

import org.akash.task.model.Task;
import org.akash.task.model.TaskExecution;
import org.akash.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return repo.findById(id);
    }

    public List<Task> searchTasksByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Task saveTask(Task task) {
        if (isUnsafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command");
        }
        return repo.save(task);
    }

    public void deleteTask(String id) {
        repo.deleteById(id);
    }

    public Task executeTask(String id) throws Exception {
        Task task = repo.findById(id).orElseThrow();
        Instant start = Instant.now();

        String output;
        try {
            output = executeShellCommand(task.getCommand());
        } catch (Exception e) {
            output = "Error: " + e.getMessage();
        }

        Instant end = Instant.now();

        TaskExecution exec = new TaskExecution(output, start, end);
        task.getTaskExecutions().add(exec);

        return repo.save(task);
    }


    public String executeShellCommand(String command) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        List<String> shellCommand;

        if (os.contains("win")) {

            shellCommand = Arrays.asList("cmd.exe", "/c", command);
        } else {

            shellCommand = Arrays.asList("sh", "-c", command);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(shellCommand);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        return output.toString().trim();
    }



    private boolean isUnsafe(String cmd) {
        return cmd.matches(".*(rm|shutdown|reboot|mkfs|;|\\||&&).*");
    }

}


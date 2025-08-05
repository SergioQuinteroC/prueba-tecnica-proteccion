package com.proteccion.prueba.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proteccion.prueba.demo.entities.Task;
import com.proteccion.prueba.demo.entities.User;
import com.proteccion.prueba.demo.repositories.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        User assignedTo = userService.getUserById(task.getAssignedTo().getId());
        if (assignedTo == null) {
            throw new RuntimeException("User assigned to task not found");
        } else {
            task.setAssignedTo(assignedTo);
        }
        User createdBy = userService.getUserById(task.getCreatedBy().getId());
        if (createdBy == null) {
            throw new RuntimeException("User created by task not found");
        } else {
            task.setCreatedBy(createdBy);
        }
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> updateTask(Long id, Task task) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(task.getTitle());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setStatus(task.getStatus());
                    existingTask.setDueDate(task.getDueDate());
                    existingTask.setAssignedTo(task.getAssignedTo());
                    existingTask.setCreatedBy(task.getCreatedBy());
                    return taskRepository.save(existingTask);
                });
    }

    @Override
    public Optional<Task> deleteTask(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return task;
                });
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    @Override
    public List<Task> getTasksByCreatedById(Long userId) {
        return taskRepository.findByCreatedById(userId);
    }

    @Override
    public List<Task> getTasksByAssignedToId(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

}

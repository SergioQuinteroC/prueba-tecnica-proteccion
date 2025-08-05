package com.proteccion.prueba.demo.services;

import java.util.List;
import java.util.Optional;

import com.proteccion.prueba.demo.entities.Task;

public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task);

    Optional<Task> updateTask(Long id, Task task);

    Optional<Task> deleteTask(Long id);

    List<Task> getTasksByUserId(Long userId);

    List<Task> getTasksByCreatedById(Long userId);

    List<Task> getTasksByAssignedToId(Long userId);

}

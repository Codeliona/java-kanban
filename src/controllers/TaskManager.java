package controllers;

import models.*;
import java.util.*;

public interface TaskManager {
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);
    void deleteAllTasks();
    void deleteAllSubtasks();
    void deleteAllEpics();
    void deleteTaskById(int id);
    void deleteSubtaskById(int id);
    void deleteEpicById(int id);
    ArrayList<Task> getAllTasks();
    ArrayList<Epic> getAllEpics();
    ArrayList<Subtask> getAllSubtasks();
    Task createTask(Task originalTask);
    Epic createEpic(Epic originalEpic);
    Subtask createSubtask(Subtask subtask);
    void updateTask(Task updatedTask);
    void updateSubtask(Subtask updatedSubtask);
    void updateEpic(Epic updatedEpic);
    void removeFromHistory(int id);
    ArrayList<Subtask> getSubtasksForEpic(Epic epic);
    List<Task> getHistory();
}

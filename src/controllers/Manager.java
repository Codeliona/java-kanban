package controllers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.util.*;

public class Manager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int counter = 0;

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        for (Subtask subtask : subtasks.values()) {
            subtask.setParentEpicId(-1);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
        subtasks.remove(id);
        epics.remove(id);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void createTask(String taskName, String description, Status status) {
        int id = generateUniqueID();
        Task task = new Task(taskName, description, status, id);
        tasks.put(id, task);
    }

    public void createSubtask(String taskName, String description, Status status, int parentEpicId) {
        int id = generateUniqueID();
        Subtask subtask = new Subtask(taskName, description, status, id, parentEpicId);
        subtasks.put(id, subtask);
    }

    public void createEpic(String taskName, String description, Status status) {
        int id = generateUniqueID();
        Epic epic = new Epic(taskName, description, status, id);
        epics.put(id, epic);
    }

    public void updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            tasks.put(updatedTask.getId(), updatedTask);
        }
    }

    public void updateSubtask(Subtask updatedSubtask) {
        if (subtasks.containsKey(updatedSubtask.getId())) {
            subtasks.put(updatedSubtask.getId(), updatedSubtask);
        }
    }

    public void updateEpic(Epic updatedEpic) {
        if (epics.containsKey(updatedEpic.getId())) {
            epics.put(updatedEpic.getId(), updatedEpic);
        }
    }

    public ArrayList<Subtask> getSubtasksForEpic(Epic epic) {
        ArrayList<Subtask> subtasksForEpic = new ArrayList<>();
        for (Integer id : epic.getSubTaskIds()) {
            if (subtasks.containsKey(id)) {
                subtasksForEpic.add(subtasks.get(id));
            }
        }
        return subtasksForEpic;
    }

    private int generateUniqueID() {
        return counter++;
    }
}

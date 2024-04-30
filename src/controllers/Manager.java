package controllers;

import models.*;
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
        epics.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
            epic.setStatus(Status.DONE);
        }
        subtasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
        }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask toRemove = subtasks.get(id);
        if (toRemove != null){
            Epic parentEpic = epics.get(toRemove.getParentEpicId());
            if (parentEpic != null){
                parentEpic.getSubTaskIds().remove(Integer.valueOf(id));
                parentEpic.updateStatus();
            }
        }
        subtasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic toRemove = epics.get(id);
        if (toRemove != null) {
            for (Integer subtaskId : new ArrayList<>(toRemove.getSubTaskIds())) {
                deleteSubtaskById(subtaskId);
            }
        }
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

    public Task createTask(Task originalTask) {
        int id = generateUniqueID();
        originalTask.setId(id);
        tasks.put(id, originalTask);
        return originalTask;
    }

    public Epic createEpic(Epic originalEpic) {
        int id = generateUniqueID();
        originalEpic.setId(id);
        epics.put(id, originalEpic);
        return originalEpic;
    }

    public Subtask createSubtask(Subtask subtask) {
        int id = generateUniqueID();
        subtask.setId(id);
        subtasks.put(id, subtask);
        getEpic(subtask.getParentEpicId()).addSubtask(subtask);
        return subtask;
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

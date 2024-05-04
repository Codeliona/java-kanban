package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
        private final HashMap<Integer, Task> tasks = new HashMap<>();
        private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
        private final HashMap<Integer, Epic> epics = new HashMap<>();
        private int counter = 0;
        private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
        @Override
        public Task getTask(int id) {
            return tasks.get(id);
        }

        @Override
        public Epic getEpic(int id) {
            return epics.get(id);
        }

        @Override
        public Subtask getSubtask(int id) {
            return subtasks.get(id);
        }

        @Override
        public void deleteAllTasks() {
            tasks.clear();
            subtasks.clear();
            epics.clear();
        }

        @Override
        public void deleteAllSubtasks() {
            for (Epic epic : epics.values()) {
                epic.getSubTaskIds().clear();
                epic.setStatus(Status.NEW);
            }
            subtasks.clear();
        }

        @Override
        public void deleteAllEpics() {
            epics.clear();
            subtasks.clear();
        }

        @Override
        public void deleteTaskById(int id) {
            tasks.remove(id);
        }

        @Override
        public void deleteSubtaskById(int id) {
            Subtask toRemove = subtasks.remove(id);
            if (toRemove != null) {
                int parentEpicId = toRemove.getParentEpicId();
                Epic parentEpic = getEpic(parentEpicId);
                if (parentEpic != null) {
                    parentEpic.getSubTaskIds().remove(Integer.valueOf(id));
                    updateEpicStatus(parentEpicId);
                }
            }
        }

        @Override
        public void deleteEpicById(int id) {
            Epic toRemove = epics.remove(id);
            if (toRemove != null) {
                for (Integer subtaskId : new ArrayList<>(toRemove.getSubTaskIds())) {
                    deleteSubtaskById(subtaskId);
                }
            }
        }

        @Override
        public ArrayList<Task> getAllTasks() {
            return new ArrayList<>(tasks.values());
        }

        @Override
        public ArrayList<Epic> getAllEpics() {
            return new ArrayList<>(epics.values());
        }

        @Override
        public ArrayList<Subtask> getAllSubtasks() {
            return new ArrayList<>(subtasks.values());
        }
        @Override
        public ArrayList<Subtask> getSubtasksForEpic(Epic epic) {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
                 for(int id : epic.getSubTaskIds()) {
                   subtaskList.add(subtasks.get(id));
        }
        return subtaskList;
    }

        @Override
        public Task createTask(Task originalTask) {
            int id = generateUniqueID();
            originalTask.setId(id);
            tasks.put(id, originalTask);
            return originalTask;
        }

        @Override
        public Epic createEpic(Epic originalEpic) {
            int id = generateUniqueID();
            originalEpic.setId(id);
            epics.put(id, originalEpic);
            return originalEpic;
        }

        @Override
        public Subtask createSubtask(Subtask subtask) {
            int id = generateUniqueID();
            subtask.setId(id);
            subtasks.put(id, subtask);
            int parentEpicId = subtask.getParentEpicId();
            Epic parentEpic = getEpic(parentEpicId);
            if (parentEpic != null) {
                parentEpic.addSubtask(subtask);
                updateEpicStatus(parentEpicId);
            }
            return subtask;
        }

        @Override
        public void updateTask(Task updatedTask) {
            if (tasks.containsKey(updatedTask.getId())) {
                tasks.put(updatedTask.getId(), updatedTask);
            }
        }

        @Override
        public void updateSubtask(Subtask updatedSubtask) {
            if (subtasks.containsKey(updatedSubtask.getId())) {
                subtasks.put(updatedSubtask.getId(), updatedSubtask);
                int parentEpicId = updatedSubtask.getParentEpicId();
                updateEpicStatus(parentEpicId);
            }
        }

        @Override
        public void updateEpic(Epic updatedEpic) {
            if (epics.containsKey(updatedEpic.getId())) {
                epics.put(updatedEpic.getId(), updatedEpic);
                int epicId = updatedEpic.getId();
                updateEpicStatus(epicId);
            }
        }

        private void updateEpicStatus(int epicId) {
            Epic currentEpic = epics.get(epicId);
            if (currentEpic != null) {
                int doneCount = 0;
                int inProgressCount = 0;

                for (Integer id : currentEpic.getSubTaskIds()) {
                    Subtask subtask = getSubtask(id);
                    if (subtask != null) {
                        Status status = subtask.getStatus();
                        if (status == Status.DONE) {
                            doneCount++;
                        } else if (status == Status.IN_PROGRESS) {
                            inProgressCount++;
                        }
                    }
                }

                if (doneCount == currentEpic.getSubTaskIds().size()) {
                    currentEpic.setStatus(Status.DONE);
                } else if (inProgressCount > 0) {
                    currentEpic.setStatus(Status.IN_PROGRESS);
                } else {
                    currentEpic.setStatus(Status.NEW);
                }
            }
        }

        private int generateUniqueID() {
            return counter++;
        }
    }


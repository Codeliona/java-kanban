import java.util.*;

public class Manager {
    private final HashMap<Integer, Task> allTasks = new HashMap<>();
    private int counter = 0;

    private int generateUniqueID() {
        return counter++;
    }

    public Task getTask(int id) {
        return allTasks.get(id);
    }

    public void deleteAllTasks() {
        allTasks.clear();
    }

    public void deleteTaskById(int id) {
        allTasks.remove(id);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(allTasks.values());
    }

    public void createTask(String taskName, String description, Task.Status status) {
        int id = generateUniqueID();
        Task task = new Task(taskName, description, status, id);
        allTasks.put(id, task);
    }

    public void createEpic(String taskName, String description, Task.Status status) {
        int id = generateUniqueID();
        Epic epic = new Epic(taskName, description, status, id);
        allTasks.put(id, epic);
    }

    public void createSubtask(String taskName, String description, Task.Status status, Epic parentEpic) {
        int id = generateUniqueID();
        Subtask subtask = new Subtask(taskName, description, status, id, parentEpic);
        allTasks.put(id, subtask);
    }

    public void updateTask(Task updatedTask) {
        if (!allTasks.containsKey(updatedTask.getId())) {
            throw new RuntimeException("Задача с id " + updatedTask.getId() + " не существует, обновление задачи невозможно");
        } else {
            allTasks.put(updatedTask.getId(), updatedTask);
        }
    }

    public ArrayList<Subtask> getSubtasksForEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTasks().values());
    }
}
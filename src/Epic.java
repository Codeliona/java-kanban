import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(String taskName, String description, Status status, int id) {
        super(taskName, description, status, id);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return new HashMap<>(subtasks);
    }
}

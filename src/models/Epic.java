package models;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String taskName, String description, Status status, int id) {
        super(taskName, description, status, id);
    }

    public void addSubtask(Subtask subtask) {
        subtaskIds.add(subtask.getId());
        subtask.setParentEpicId(this.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        return new ArrayList<>(subtaskIds);
    }
}

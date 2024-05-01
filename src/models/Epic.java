package models;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public void addSubtask(Subtask subtask) {
        subtaskIds.add(subtask.getId());
        subtask.setParentEpicId(this.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", id=" + getId() +
                ", taskName='" + getTaskName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}



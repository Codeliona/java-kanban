package models;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();
    private final ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public void addSubtask(Subtask subtask) {
        subtaskIds.add(subtask.getId());
        subtask.setParentEpicId(this.getId());
        updateStatus();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void updateStatus() {
        int doneCount = 0;
        int inProgressCount = 0;

        for (Subtask subtask : subtasks) {
            Status status = subtask.getStatus();
            if (status == Status.DONE) {
                doneCount++;
            } else if (status == Status.IN_PROGRESS) {
                inProgressCount++;
            }
        }

        if (doneCount == subtasks.size()) {
            setStatus(Status.DONE);
        } else if (inProgressCount > 0) {
            setStatus(Status.IN_PROGRESS);
        } else {
            setStatus(Status.NEW);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", id=" + getId() +
                ", taskName='" + getTaskName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}



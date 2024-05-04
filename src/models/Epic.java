package models;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public void addSubtask(Subtask subtask) {
        if (subtask.getId() == this.getId()) {
            throw new IllegalArgumentException("Эпик не может добавляться в себя в качестве подзадачи");
        }
        subtasks.add(subtask);
        subtask.setParentEpicId(this.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        ArrayList<Integer> subtaskIDList = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            subtaskIDList.add(subtask.getId());
        }
        return subtaskIDList;
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
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



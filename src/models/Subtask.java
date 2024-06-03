package models;

import java.util.Objects;

public class Subtask extends Task {
    private int parentEpicId;

    public Subtask(String taskName, String description, Status status, int parentEpicId) {
        super(taskName, description, status);
        this.parentEpicId = parentEpicId;
    }

    public int getParentEpicId() {
        return parentEpicId;
    }

    public void setParentEpicId(int parentEpicId) {
        if (this.getId() == parentEpicId) {
            throw new IllegalArgumentException("Подзадача не может устанавливать себя в качестве родительского эпоса");
        }
        this.parentEpicId = parentEpicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        Subtask subtask = (Subtask) o;
        return getId() == subtask.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "parentEpicId=" + parentEpicId +
                ", id=" + getId() +
                ", taskName='" + getTaskName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}

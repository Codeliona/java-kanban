package models;

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
        this.parentEpicId = parentEpicId;
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

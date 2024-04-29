package models;

public class Subtask extends Task {
    private int parentEpicId;

    public Subtask(String taskName, String description, Status status, int id, int parentEpicId) {
        super(taskName, description, status, id);
        this.parentEpicId = parentEpicId;
    }

    public int getParentEpicId() {
        return parentEpicId;
    }

    public void setParentEpicId(int parentEpicId) {
        this.parentEpicId = parentEpicId;
    }
}

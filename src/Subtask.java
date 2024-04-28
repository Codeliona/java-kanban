public class Subtask extends Task {
    private Epic parentEpic;

    public Subtask(String taskName, String description, Status status, int id, Epic parentEpic) {
        super(taskName, description, status, id);
        this.parentEpic = parentEpic;
        parentEpic.addSubtask(this);
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }
}

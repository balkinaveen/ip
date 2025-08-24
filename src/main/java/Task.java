public class Task {
    private final String taskDescription;
    private boolean isDone;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        isDone = false;
    }

    public void mark() {
        isDone = true;
    }

    public void unMark() {
        isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X] " : "[ ] ");
    }

    public String description() {
        return this.taskDescription;
    }

    @Override
    public String toString() {
        return getStatusIcon() + taskDescription;
    }
}

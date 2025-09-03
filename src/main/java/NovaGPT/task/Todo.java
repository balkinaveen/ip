package NovaGPT.task;

/**
 * Represents an Todo task
 * Each task has a description and a completion status
 */
public class Todo extends Task{
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

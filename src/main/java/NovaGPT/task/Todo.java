package novagpt.task;

/**
 * Represents an Todo task
 * A todo has only description and a completion status
 */
public class Todo extends Task {
    /**
     * Constructs a  Todo with the given description.
     * @param description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

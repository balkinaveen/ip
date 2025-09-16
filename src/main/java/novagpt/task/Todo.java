package novagpt.task;

/**
 * Represents a Todo task.
 * A Todo task contains only a description and a completion status.
 *
 * <p>
 */
public class Todo extends Task {
    /**
     * Constructs a {@code Todo} task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task.
     *
     * @return A string in the format: {@code [T][X] description},
     *         where [T] represents a todo, [X] is the status icon, and {@code description} is the task detail.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

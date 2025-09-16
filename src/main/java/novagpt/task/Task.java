package novagpt.task;

/**
 * Represents a generic abstract Task.
 * Subclasses such as {@code Todo}, {@code Deadline}, and {@code Event} extend this class.
 * <p>
 */
public abstract class Task {
    private static final String DONE_ICON = "[X] ";
    private static final String NOT_DONE_ICON = "[ ] ";
    private final String taskDescription;
    private boolean isDone;

    /**
     * Constructs a {@code Task} object with the given description.
     *
     * @param taskDescription Description of the task.
     */
    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the visual icon indicating the completion status of the task.
     *
     * @return {@code [X]} if the task is completed, {@code [ ]} otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? DONE_ICON : NOT_DONE_ICON);
    }

    /**
     * Returns the boolean status of the task.
     *
     * @return {@code true} if completed, {@code false} otherwise.
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * Returns the string representation of the task, including status icon and description.
     *
     * @return Task string with status icon and description.
     */
    @Override
    public String toString() {
        return getStatusIcon() + taskDescription.trim();
    }
}

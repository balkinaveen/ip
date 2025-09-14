package novagpt.task;

/**
 * This is abstract (base) class for a generic tasks
 * Subclasses include Todo, Deadline and Event
 */
public abstract class Task {
    private static final String DONE_ICON = "[X] ";
    private static final String NOT_DONE_ICON = "[ ] ";
    private final String taskDescription;
    private boolean isDone;

    /**
     * Constructs a task object with the given description
     * @param taskDescription  the Description of the task
     */
    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        isDone = false;
    }

    /**
     * Marks a task as done
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Unmarks a task as not done
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the status icon to a task
     * "[X]" if done, "[ ]" if not done
     */
    public String getStatusIcon() {
        return (isDone ? DONE_ICON : NOT_DONE_ICON);
    }

    /**
     * gets the completion status of a task
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Gets the description of the task
     */
    public String getTaskDescription() {
        return this.taskDescription;
    }

    @Override
    public String toString() {
        return getStatusIcon() + taskDescription.trim();
    }
}

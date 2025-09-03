package NovaGPT.task;

/**
 * Represents a generic task
 * Each task has a description and a completion status.
 * This is abstract (base) class for more specific task types
 * including Todo, Deadline and Event
 */
public abstract class Task {
    private final String taskDescription;
    private boolean isDone;

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
    public void unMark() {
        isDone = false;
    }

    /**
     * Gets the completion status of a task and converts it in an icon to
     * be used in toString method
     */
    public String getStatusIcon() {
        return (isDone ? "[X] " : "[ ] ");
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

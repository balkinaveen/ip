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
     * marks a task as done
     */
    public void mark() {
        isDone = true;
    }

    /**
     * unmarks a task as not done
     */
    public void unMark() {
        isDone = false;
    }

    /**
     * gets the completion status of a task and converts it in an icon to
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
     * gets the description of the task
     */
    public String getTaskDescription() {
        return this.taskDescription;
    }

    @Override
    public String toString() {
        return getStatusIcon() + taskDescription.trim();
    }
}

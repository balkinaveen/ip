package novagpt.ui;

import java.util.ArrayList;

import novagpt.task.Task;

/**
 * The {@code Ui} class handles all user-facing interactions in the NovaGPT chatbot.
 * It provides preformatted response messages for various task-related operations such as
 * adding, marking, listing, and removing tasks, as well as for general interactions like
 * greetings, errors, and help prompts.
 *
 * <p>This class also stores commonly used command format strings and error messages
 * for consistency across the chatbot's user interface.</p>
 */
public class Ui {
    /** Kill switch input to terminate the program. */
    public static final String KILL_SWITCH = "bye";
    /** Command format for adding a todo task. */
    public static final String TODO_COMMAND_FORMAT = "todo <task name>";
    /** Command format for adding a deadline task. */
    public static final String DEADLINE_COMMAND_FORMAT = "deadline <task name> /by <deadline> "
            + "DD/MM/YYYY HHMM (24 hour)";
    /** Command format for adding an event task. */
    public static final String EVENT_COMMAND_FORMAT = "event <task name> "
            + "/from <start time> DD/MM/YYYY HHMM (24 hour) /to <end time> DD/MM/YYYY HHMM (24 hour)";
    /** Command format for finding tasks by keyword. */
    public static final String FIND_COMMAND_FORMAT = "find <keyword(s) to search>";
    /** Command format for deleting a task. */
    public static final String DELETE_COMMAND_FORMAT = "delete <Task number>";
    /** Command format for marking a task as done. */
    public static final String MARK_COMMAND_FORMAT = "mark <Task number>";
    /** Command format for unmarking a task. */
    public static final String UNMARK_COMMAND_FORMAT = "unmark <Task number>";
    /** Command format for listing all tasks. */
    public static final String LIST_COMMAND_FORMAT = "list";
    /** Command format for listing reminders within a certain number of days. */
    public static final String REMINDER_COMMAND_FORMAT = "reminder <number of days to remind within>";
    public static final String EMPTY_ERROR_MESSAGE = "OOPS! The description of a task cannot be empty. \n";
    public static final String FORMAT_MESSAGE = "Do format the message: ";
    public static final String OUT_OF_INDEX = "OOPS! Task number is out of range! Try again";
    public static final String LIST_OF_COMMANDS = "List of valid commands: \n"
            + TODO_COMMAND_FORMAT + "\n"
            + DEADLINE_COMMAND_FORMAT + "\n"
            + EVENT_COMMAND_FORMAT + "\n"
            + FIND_COMMAND_FORMAT + "\n"
            + DELETE_COMMAND_FORMAT + "\n"
            + MARK_COMMAND_FORMAT + "\n"
            + UNMARK_COMMAND_FORMAT + "\n"
            + LIST_COMMAND_FORMAT;


    /**
     * Returns the welcome message displayed at program startup.
     *
     * @return Greeting message.
     */
    public static String welcomeMessage() {
        //return "Hello! \nHope you're having a nice day! \nWhat can I do for you today?";
        return "✨ Greetings, Earthling! Nova reporting for task duty. What's on your mind today?";
    }

    /**
     * Returns the farewell message displayed upon exiting the program.
     *
     * @return Goodbye message.
     */
    public static String goodbyeMessage() {
        return "\uD83C\uDF20 Mission complete. Nova signing off. Stay stellar!";
    }

    /**
     * Returns a confirmation message for a successfully added task.
     *
     * @param task The task that was added.
     * @param ls The current task list containing the added task.
     * @return Formatted string confirming task addition.
     */
    public static String taskMessage(Task task, ArrayList<Task> ls) {
        return "\uD83D\uDE80 Task locked and loaded: \n"
                + task + "\nYou now have "
                + ls.size()
                + " task(s) orbiting your list!\"";
    }

    /**
     * Returns a confirmation message for marking a task as completed.
     *
     * @param task The task to mark as done.
     * @return Formatted string confirming the task is marked as done.
     */
    public static String markMessage(Task task) {
        return "🌟 Great job, Commander! Task marked as complete: \n"
                + task;
    }

    /**
     * Returns a confirmation message for marking a task as not done.
     *
     * @param task The task to unmark.
     * @return Formatted string confirming the task is unmarked.
     */
    public static String unmarkMessage(Task task) {
        return "🪐 Oops! Task reset to incomplete mode: \n"
                + task;
    }

    /**
     * Returns a formatted list of all current tasks.
     *
     * @param tasksAsString A string representation of the task list.
     * @return Formatted string listing all tasks or notifying if empty.
     */
    public static String listMessage(String tasksAsString) {
        if (tasksAsString.isEmpty()) {
            return "No tasks in your list currently! Let's start adding tasks!";
        }
        return "\uD83D\uDEF0\uFE0F Scanning your task galaxy...\n" + tasksAsString;
    }

    /**
     * Returns a confirmation message after removing a task.
     *
     * @param removed The task that was removed.
     * @param ls The current task list after removal.
     * @return Formatted string confirming task removal.
     */
    public static String removeMessage(Task removed, ArrayList<Task> ls) {
        return "\uD83D\uDCA5 Task ejected from orbit: \n"
                + removed.toString() + "\nYou now have "
                + ls.size()
                + " task(s) left.";
    }

    /**
     * Returns the result of a keyword-based search within the task list.
     *
     * @param s Formatted string of matching tasks.
     * @return Search result message.
     */
    public static String findMessage(String s) {
        return "\uD83D\uDD2D Nova found the following cosmic matches: " + s;
    }

    /**
     * Returns a formatted error message.
     *
     * @param error The specific error to display.
     * @return Formatted error message string.
     */
    public static String errorMessage(String error) {
        return error;
    }

    /**
     * Returns a generic error message for unexpected errors.
     *
     * @param error The error message or stack trace.
     * @return Generic error message string.
     */
    public static String unexpectedErrorMessage(String error) {
        return "Unexpected error: " + error;
    }

    /**
     * Returns a reminder message for upcoming deadlines and events within the specified number of days.
     *
     * @param days The time window (in days) for upcoming tasks.
     * @param reminderAsString A string representation of tasks within that window.
     * @return Formatted reminder message.
     */
    public static String reminderMessage(int days, String reminderAsString) {
        if (reminderAsString.isEmpty()) {
            return "\uD83D\uDD73\uFE0F No upcoming tasks within the next " + days + " days in your universe!";
        }
        return "\uD83D\uDCE1 Incoming transmissions: tasks due within " + days + " days" + reminderAsString;
    }
}

package novagpt.ui;

import java.util.ArrayList;

import novagpt.task.Task;

/**
 * Represents Ui interface
 * Ensures the correct output for user interactions
 */
public class Ui {

    public static final String HORIZONTAL_LINE = "____________________________________________________________";
    public static final String KILL_SWITCH = "bye";
    public static final String TODO_COMMAND_FORMAT = "todo <task name>";
    public static final String DEADLINE_COMMAND_FORMAT = "deadline <task name> /by <deadline> "
            + "DD/MM/YYYY HHMM (24 hour)";
    public static final String EVENT_COMMAND_FORMAT = "event <task name> "
            + "/from <start time> DD/MM/YYYY HHMM (24 hour) /to <end time> DD/MM/YYYY HHMM (24 hour)";
    public static final String FIND_COMMAND_FORMAT = "find <keyword(s) to search>";
    public static final String DELETE_COMMAND_FORMAT = "delete <Task number>";
    public static final String MARK_COMMAND_FORMAT = "mark <Task number>";
    public static final String UNMARK_COMMAND_FORMAT = "unmark <Task number>";
    public static final String LIST_COMMAND_FORMAT = "list";
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
     * Prints a message
     *
     * @param message the text to output to the user
     */
    public static String print(String message) {
        return message;
    }

    /**
     * Prints a welcome message with NovaGPT logo.
     */
    public static String welcomeMessage() {
        return print("Hello! \nHope you're having a nice day! \nWhat can I do for you today?");
    }

    /**
     * Prints a goodbye message
     */
    public static String goodbyeMessage() {
        return print("Bye. Hope to see you soon!\nHAND!");
    }

    /**
     * Prints the output of a task
     *
     * @param task the task that was added
     * @param ls is the list of tasks that was added
     */
    public static String taskMessage(Task task, ArrayList<Task> ls) {
        return print("Got it. I've added this task:\n"
                + task + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    /**
     * Prints the status of a marked task
     *
     * @param task the task that needs to be mark as done
     */
    public static String markMessage(Task task) {
        return print("Nice! I've marked this task as done: \n"
                + task);
    }

    /**
     * Prints the status of a unmarked task
     *
     * @param task the text to output to the user
     */
    public static String unmarkMessage(Task task) {
        return print("OK, I've marked this task as not done yet: \n"
                + task);
    }

    /**
     * Prints a list of tasks
     *
     * @param s is a string of tasks to print
     */
    public static String listMessage(String s) {
        return print(s);
    }

    /**
     * Prints the status of a removed task
     *
     * @param removed is the task that was removed
     * @param ls is the list of current tasks after the removal
     */
    public static String removeMessage(Task removed, ArrayList<Task> ls) {
        return print("Noted. I've removed this task:\n"
                + removed.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static String findMessage(String s) {
        return print("Here are the matching tasks in your list:" + s);
    }

    public static String errorMessage(String error) {
        return print(error);
    }

    public static String unexpectedErrorMessage(String error) {
        return print("Unexpected error: " + error);
    }
}

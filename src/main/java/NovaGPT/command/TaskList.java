package novagpt.command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import novagpt.exception.NovaException;
import novagpt.storage.Storage;
import novagpt.task.Deadline;
import novagpt.task.Event;
import novagpt.task.Task;
import novagpt.task.Todo;
import novagpt.ui.Ui;

/**
 * Represents a TaskList, which contains the handlers for Task orientated commands
 * This class contains methods to handle Todo, Deadline, Event tasks
 * as well as methods to handle mark, unmark, delete and list commands
 */
public class TaskList {

    /**
     * Handles Todo Tasks
     * Takes in a user input String, ArrayList and Storage
     * ensures that input is valid,
     * creates a todo task, adds it to the Arraylist and prompts ui
     * to print the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     */
    public static String handleTodoTask(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        String text = input.substring("todo".length()).trim();
        if (text.isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.TODO_COMMAND_FORMAT);
        }
        Task t = new Todo(text);
        ls.add(t);
        st.save(ls);
        return Ui.taskMessage(t, ls);

    }

    /**
     * Handles Deadline Tasks
     * Takes in a user input String, ArrayList and Storage
     * ensures that input is valid,
     * creates a deadline task, adds it to the Arraylist and prompts ui
     * to print the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     * @throws NovaException If the input is invalid
     */
    public static String handleDeadlineTask(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        String text = input.substring("deadline".length()).trim();
        if (text.isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.DEADLINE_COMMAND_FORMAT);
        } else if (!input.contains("/by")) {
            throw new NovaException(Ui.FORMAT_MESSAGE + Ui.DEADLINE_COMMAND_FORMAT);
        }
        String[] split = text.split("/by", 2);
        if (split[0].trim().isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.DEADLINE_COMMAND_FORMAT);
        }
        Task t = new Deadline(split[0].trim(), split[1].trim());
        ls.add(t);
        st.save(ls);
        return Ui.taskMessage(t, ls);
    }

    /**
     * Handles Event Tasks
     * Takes in a user input String, ArrayList and Storage
     * ensures that input is valid,
     * creates an event task, adds it to the Arraylist and prompts ui
     * to print the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     * @throws NovaException If the input is invalid
     */
    public static String handleEventTask(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        String text = input.substring("event".length()).trim();
        if (text.isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.EVENT_COMMAND_FORMAT);
        } else if (!input.contains("/from") || !input.contains("/to")) {
            throw new NovaException(
                    Ui.FORMAT_MESSAGE + Ui.EVENT_COMMAND_FORMAT);
        }
        String[] split1 = text.split("/from", 2);
        String[] split2 = split1[1].split("/to", 2);
        if (split1[0].trim().isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.EVENT_COMMAND_FORMAT);
        }
        Task t = new Event(split1[0].trim(), split2[0].trim(), split2[1].trim());
        ls.add(t);
        st.save(ls);
        return Ui.taskMessage(t, ls);
    }

    /**
     * Handles Mark Command
     * Takes in a user input String
     * ensures that input is valid and number is within range
     * marks the corresponding task as done and
     * prints the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     * @throws NovaException If the input is invalid
     */
    public static String handleMark(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = Parser.parseTaskIndex(input, "mark");
        if (listNum >= ls.size()) {
            throw new NovaException(Ui.OUT_OF_INDEX);
        }
        Task t = ls.get(listNum);
        t.mark();
        st.save(ls);
        return Ui.markMessage(t);
    }

    /**
     * Handles Unmark Command
     * Takes in a user input String
     * ensures that input is valid and number is within range
     * unmarks the corresponding task as not done and
     * prints the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     * @throws NovaException If the input is invalid
     */
    public static String handleUnmark(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = Parser.parseTaskIndex(input, "unmark");
        if (listNum >= ls.size()) {
            throw new NovaException(Ui.OUT_OF_INDEX);
        }
        Task t = ls.get(listNum);
        t.unmark();
        st.save(ls);
        return Ui.unmarkMessage(t);
    }

    /**
     * Returns a list of tasks
     * Handles list Command
     * Takes in an ArrayList
     * for each task in ArrayList, adds the details to an outout string
     * add returns the output string
     *
     * @param ls ArrayList containing all saved tasks
     */
    public static String handleList(ArrayList<Task> ls) {
        String output = "";
        int count = ls.size();
        for (int j = 0; j < count; j++) {
            if (j < count - 1) {
                output += (j + 1) + ". " + ls.get(j).toString() + "\n";
            } else {
                output += (j + 1) + ". " + ls.get(j).toString();
            }
        }
        return Ui.listMessage(output);
    }

    /**
     * Handles delete Command
     * Takes in a user input String
     * ensures that input is valid and number is within range
     * deletes the corresponding task and
     * prints the respective output message
     *
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     * @param st Storage object handling all storage related commands
     * @throws NovaException If the input is invalid
     */
    public static String handleDelete(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = Parser.parseTaskIndex(input, "delete");
        if (listNum >= ls.size()) {
            throw new NovaException(Ui.OUT_OF_INDEX);
        }
        Task removed = ls.remove(listNum);
        st.save(ls);
        return Ui.removeMessage(removed, ls);
    }

    /**
     * Handles find Command
     * Takes in a user input String
     * ensures that input is valid
     * finds all tasks containing the search String and
     * prints a list
     */
    public static String handleFind(String input, ArrayList<Task> ls) throws NovaException {
        String searchString = input.substring(4).trim();
        if (searchString.isEmpty()) {
            throw new NovaException(Ui.EMPTY_ERROR_MESSAGE + Ui.FORMAT_MESSAGE + Ui.FIND_COMMAND_FORMAT);
        }
        String output = "";
        int counter = 1;
        for (int i = 0; i < ls.size(); i++) {
            Task task = ls.get(i);
            if (task.getTaskDescription().contains(searchString)) {
                output += "\n" + counter + "." + task;
                counter++;
            }
        }
        return Ui.findMessage(output);
    }

    /**
     * Returns reminders for tasks with upcoming deadlines/events
     * This checks tasks within the next given number of days
     * @param input The input string that the user provides
     * @param ls ArrayList containing all saved tasks
     */
    public static String handleReminders(String input, ArrayList<Task> ls) throws NovaException {
        int days = Parser.parseTaskIndex(input, "reminder") + 1;
        LocalDateTime presentDateTime = LocalDateTime.now();
        LocalDateTime cutoffDateTime = presentDateTime.plusDays(days);
        String out = "";
        int counter = 1;

        for (Task task : ls) {
            if (task instanceof Deadline) {
                Deadline deadlineTask = (Deadline) task;
                LocalDateTime dEndTimeDate = deadlineTask.getEndTimeAndDate();
                Boolean isAfterToday = dEndTimeDate.isAfter(presentDateTime);
                Boolean isBeforeCutoff = dEndTimeDate.isBefore(cutoffDateTime);
                Boolean isWithWindow = isAfterToday && isBeforeCutoff;
                if (isWithWindow) {
                    out += "\n" + counter + ". " + deadlineTask;
                    counter++;
                }
            } else if (task instanceof Event) {
                Event eventTask = (Event) task;
                LocalDateTime dEndTimeDate = eventTask.getStartTimeAndDate();
                Boolean isAfterToday = dEndTimeDate.isAfter(presentDateTime);
                Boolean isBeforeCutoff = dEndTimeDate.isBefore(cutoffDateTime);
                Boolean isWithWindow = isAfterToday && isBeforeCutoff;
                if (isWithWindow) {
                    out += "\n" + counter + ". " + eventTask;
                    counter++;
                }
            }
        }
        return Ui.reminderMessage(days, out);
    }
}

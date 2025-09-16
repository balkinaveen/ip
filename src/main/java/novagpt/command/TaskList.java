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
 * Handles the execution of task-related commands.
 * <p>
 * This class provides static methods to manage operations on the task list,
 * including adding new tasks (Todo, Deadline, Event), updating task status (mark/unmark),
 * displaying task list, deleting tasks, searching for keywords, and setting reminders.
 * </p>
 *
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
     * Handles the creation of a Deadline task.
     *
     * @param input The full user input (e.g. "deadline submit report /by 20/09/2025 2359")
     * @param ls The current list of tasks
     * @param st The Storage instance used to persist tasks
     * @return Confirmation message from the Ui
     * @throws NovaException If the input is missing description or deadline, or incorrectly formatted
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
     * Handles the creation of an Event task.
     *
     * @param input The full user input (e.g. "event CS2103T Exam /from 01/10/2025 1400 /to 01/10/2025 1600")
     * @param ls The current list of tasks
     * @param st The Storage instance used to persist tasks
     * @return Confirmation message from the Ui
     * @throws NovaException If the input is missing description, start/end time, or incorrectly formatted
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
     * Marks a task as done.
     *
     * @param input The full user input (e.g. "mark 1")
     * @param ls The current list of tasks
     * @param st The Storage instance used to persist tasks
     * @return Confirmation message from the Ui
     * @throws NovaException If the task index is invalid
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
     * Marks a task as not done.
     *
     * @param input The full user input (e.g. "unmark 1")
     * @param ls The current list of tasks
     * @param st The Storage instance used to persist tasks
     * @return Confirmation message from the Ui
     * @throws NovaException If the task index is invalid
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
     * Returns a formatted string representing the full list of tasks.
     *
     * @param ls The current list of tasks
     * @return A formatted list of tasks from the Ui
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
     * Deletes a task from the list.
     *
     * @param input The full user input (e.g. "delete 2")
     * @param ls The current list of tasks
     * @param st The Storage instance used to persist tasks
     * @return Confirmation message from the Ui
     * @throws NovaException If the task index is invalid
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
     * Finds and returns a list of tasks that contain a given keyword.
     *
     * @param input The full user input (e.g. "find exam")
     * @param ls The current list of tasks
     * @return A list of matching tasks from the Ui
     * @throws NovaException If the search string is empty
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
                output += "\n" + counter + ". " + task;
                counter++;
            }
        }
        return Ui.findMessage(output);
    }

    /**
     * Provides reminders for upcoming deadlines or events within a specified number of days.
     *
     * @param input The full user input (e.g. "reminder 3")
     * @param ls The current list of tasks
     * @return A reminder message listing tasks due within the timeframe
     * @throws NovaException If the input format is invalid
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

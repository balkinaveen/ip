package NovaGPT.command;

import NovaGPT.task.Task;
import NovaGPT.task.Todo;
import NovaGPT.task.Deadline;
import NovaGPT.task.Event;
import NovaGPT.ui.Ui;
import NovaGPT.storage.Storage;
import NovaGPT.exception.NovaException;
import java.util.ArrayList;

public class TaskList {
    private static final String MARKED_MESSAGE = "Nice! I've marked this task as done:";
    private static final String UNMARKED_MESSAGE = "OK, I've marked this task as not done yet:";

    public static void handleTodoTask(String input, ArrayList<Task> ls, Storage st) {
        String text = input.substring(4).trim();
        if (text.isEmpty()) {
            Ui.print("OOPS! The description of a todo cannot be empty. " +
                    "\nDo format your input: todo <task>");
            return;
        }
        Task t = new Todo(text);
        ls.add(t);
        st.save(ls);
        Ui.print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleDeadlineTask(String input, ArrayList<Task> ls, Storage st) throws NovaException{
        String text = input.substring(8).trim();
        if (text.isEmpty()) {
            Ui.print("OOPS! The description of a deadline cannot be empty. " +
                    "\nDo format your input: deadline <task> /by <deadline> DD/MM/YYYY HHMM (24 hour)");
            return;
        } else if (!input.contains("/by")) {
            Ui.print("OOPS! The description of a deadline must contain /by. " +
                    "\nDo format your input: deadline <task> /by <deadline> DD/MM/YYYY HHMM (24 hour)");
            return;
        }
        String[] split = text.split("/by", 2);
        if (split[0].trim().isEmpty()) {
            Ui.print("OOPS! The description of a event cannot be empty. " +
                    "\nDo format your input: deadline <task> /by <deadline> DD/MM/YYYY HHMM (24 hour)");
            return;
        }
        Task t = new Deadline(split[0].trim(),split[1].trim());
        ls.add(t);
        st.save(ls);
        Ui.print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleEventTask(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        String text = input.substring(5).trim();
        if (text.isEmpty()) {
            Ui.print("OOPS! The description of a event cannot be empty. " +
                    "\nDo format your input: event <task> /from <start> DD/MM/YYYY HHMM (24 hour) /to <end> DD/MM/YYYY HHMM (24 hour)");
            return;
        } else if (!input.contains("/from") || !input.contains("/to")) {
            Ui.print("OOPS! The description of an event must contain /by. " +
                    "\nDo format your input: event <task> /from <start> DD/MM/YYYY HHMM (24 hour) /to <end> DD/MM/YYYY HHMM (24 hour)");
            return;
        }
        String[] split1 = text.split("/from", 2);
        String[] split2 = split1[1].split("/to", 2);
        if (split1[0].trim().isEmpty()) {
            Ui.print("OOPS! The description of an event cannot be empty. " +
                    "\nDo format your input: event <task> /from <start> DD/MM/YYYY HHMM (24 hour) /to <end> DD/MM/YYYY HHMM (24 hour)");
            return;
        }
        Task t = new Event(split1[0].trim(), split2[0].trim(), split2[1].trim());
        ls.add(t);
        st.save(ls);
        Ui.print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleMark(String input, ArrayList<Task> ls, Storage st) throws NovaException{
        int listNum = Parser.parseTaskIndex(input, "mark");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task t = ls.get(listNum);
        t.mark();
        st.save(ls);
        Ui.print(MARKED_MESSAGE + "\n" + t.toString());
    }

    public static void handleUnMark(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = Parser.parseTaskIndex(input, "unmark");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task t = ls.get(listNum);
        t.unMark();
        st.save(ls);
        Ui.print(UNMARKED_MESSAGE + "\n" + t.toString());
    }

    public static String handleList(ArrayList<Task> ls) {
        String output  = "";
        int count = ls.size();
        for(int j = 0; j < count; j++) {
            if (j < count - 1) {
                output += (j + 1) + ". " + ls.get(j).toString() + "\n";
            } else {
                output += (j + 1) + ". " + ls.get(j).toString();
            }
        }
        return output;
    }

    public static void handleDelete(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = Parser.parseTaskIndex(input, "delete");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task removed = ls.remove(listNum);
        st.save(ls);
        Ui.print("Noted. I've removed this task:\n"
                + removed.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }
}

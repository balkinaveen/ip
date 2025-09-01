import java.util.Scanner;
import java.util.ArrayList;

public class NovaGPT {
    private static final String WELCOME_MESSAGE = "Hello, I'm NovaGPT!\nWhat can I do for you today?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you soon!\nHAND!";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String MARKED_MESSAGE = "Nice! I've marked this task as done:";
    private static final String UNMARKED_MESSAGE = "OK, I've marked this task as not done yet:";
    private static final String KILL_SWITCH = "bye";
    private static final String DATA_PATH = "./data/NovaGPT.txt";

    public enum Command {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        UNKNOWN
    }

    public static Command parseCommandFromInput(String input) {
        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.equals(KILL_SWITCH)) return Command.BYE;
        if (lowerCaseInput.equals("list")) return Command.LIST;
        if (lowerCaseInput.startsWith("mark")) return Command.MARK;
        if (lowerCaseInput.startsWith("unmark")) return Command.UNMARK;
        if (lowerCaseInput.startsWith("todo")) return Command.TODO;
        if (lowerCaseInput.startsWith("deadline")) return Command.DEADLINE;
        if (lowerCaseInput.startsWith("event")) return Command.EVENT;
        if (lowerCaseInput.startsWith("delete")) return Command.DELETE;
        return Command.UNKNOWN;
    }

    public static int parseTaskIndex(String input, String command) throws NovaException {
        try {
            String numberString = input.substring(command.length()).trim();
            int index = Integer.parseInt(numberString) - 1;
            if (index < 0) {
                throw new NovaException("OOPS! The task number must be positive!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new NovaException("OOPS! Please enter a valid number! \nDo format your input: " + command + " <number>");
        }
    }

    public static void print(String message) {
        System.out.print(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE + "\n");
    }

    public static void handleTodoTask(String input, ArrayList<Task> ls, Storage st) {
        String text = input.substring(4).trim();
        if (text.isEmpty()) {
            print("OOPS! The description of a todo cannot be empty. " +
                    "\nDo format your input: todo <task>");
            return;
        }
        Task t = new Todo(text);
        ls.add(t);
        st.save(ls);
        print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleDeadlineTask(String input, ArrayList<Task> ls, Storage st) {
        String text = input.substring(8).trim();
        if (text.isEmpty()) {
            print("OOPS! The description of a deadline cannot be empty. " +
                    "\nDo format your input: deadline <task> /by <deadline>");
            return;
        } else if (!input.contains("/by")) {
            print("OOPS! The description of a deadline must contain /by. " +
                    "\nDo format your input: deadline <task> /by <deadline>");
            return;
        }
        String[] split = text.split("/by", 2);
        Task t = new Deadline(split[0].trim(),split[1].trim());
        ls.add(t);
        st.save(ls);
        print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleEventTask(String input, ArrayList<Task> ls, Storage st) {
        String text = input.substring(5).trim();
        if (text.isEmpty()) {
            print("OOPS! The description of a event cannot be empty. " +
                    "\nDo format your input: event <task> /from <start> /to <end>");
            return;
        } else if (!input.contains("/from") || !input.contains("/to")) {
            print("OOPS! The description of a event must contain /by. " +
                    "\nDo format your input: event <task> /from <start> /to <end>");
            return;
        }
        String[] split1 = text.split("/from", 2);
        String[] split2 = split1[1].split("/to", 2);
        Task t = new Event(split1[0].trim(), split2[0].trim(), split2[1].trim());
        ls.add(t);
        st.save(ls);
        print("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void handleMark(String input, ArrayList<Task> ls, Storage st) throws NovaException{
        int listNum = parseTaskIndex(input, "mark");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task t = ls.get(listNum);
        t.mark();
        st.save(ls);
        print(MARKED_MESSAGE + "\n" + t.toString());
    }

    public static void handleUnMark(String input, ArrayList<Task> ls, Storage st) throws NovaException {
        int listNum = parseTaskIndex(input, "unmark");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task t = ls.get(listNum);
        t.unMark();
        st.save(ls);
        print(UNMARKED_MESSAGE + "\n" + t.toString());
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
        int listNum = parseTaskIndex(input, "delete");
        if (listNum >= ls.size()) {
            throw new NovaException("OOPS! Task number is out of range! Try again");
        }
        Task removed = ls.remove(listNum);
        st.save(ls);
        print("Noted. I've removed this task:\n"
                + removed.toString() + "\nNow you have "
                + ls.size()
                + " tasks in the list.");
    }

    public static void run() {
        print(WELCOME_MESSAGE);
        String input = "";
        Scanner sc = new Scanner(System.in);
        Storage st = new Storage(DATA_PATH);
        ArrayList<Task> ls = st.load();

        while(!input.toLowerCase().equals(KILL_SWITCH)) {
            input = sc.nextLine();
            Command command = parseCommandFromInput(input);
            try {
                switch (command) {
                    case BYE:
                        break;
                    case LIST:
                        print(handleList(ls));
                        break;
                    case MARK:
                        handleMark(input, ls, st);
                        break;
                    case UNMARK:
                        handleUnMark(input, ls, st);
                        break;
                    case TODO:
                        handleTodoTask(input, ls, st);
                        break;
                    case DEADLINE:
                        handleDeadlineTask(input, ls, st);
                        break;
                    case EVENT:
                        handleEventTask(input, ls, st);
                        break;
                    case DELETE:
                        handleDelete(input, ls, st);
                        break;
                    case UNKNOWN:
                        throw new NovaException("Hold up! I'm sorry but I don't get what that means, please try again :-(");
                }
            } catch (NovaException e) {
                    print(e.getMessage());
            } catch (Exception e) {
                    print("Unexpected error: " + e.getMessage());
            }
        }
        print(GOODBYE_MESSAGE);
    }

    public static void main(String[] args) {
        NovaGPT.run();
    }
}

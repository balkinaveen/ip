import java.util.List;
import java.util.Scanner;

public class NovaGPT {
    private static final String welcomeMessage = "Hello, I'm NovaGPT!\nWhat can I do for you today?";
    private static final String goodbyeMessage = "Bye. Hope to see you soon!\nHAND!";
    private static final String horizontalLine = "____________________________________________________________";
    private static final String markedMessage = "Nice! I've marked this task as done:";
    private static final String unMarkedMessage = "OK, I've marked this task as not done yet:";
    private static final String killswitch = "bye";

    public static void printer(String message) {
        System.out.print(horizontalLine + "\n" + message + "\n" + horizontalLine + "\n");
    }

    public static void handlerTodoTask(String input, Lists ls) {
        String text = input.substring(4).trim();
        if (text.isEmpty()) {
            printer("OOPS! The description of a todo cannot be empty. " +
                    "\nDo format your input: todo <task>");
            return;
        }
        ls.add(new Todo(text));
    }

    public static void handlerDeadlineTask(String input, Lists ls) {
        String text = input.substring(8).trim();
        if (text.isEmpty()) {
            printer("OOPS! The description of a deadline cannot be empty. " +
                    "\nDo format your input: deadline <task> /by <deadline>");
            return;
        } else if (!input.contains("/by")) {
            printer("OOPS! The description of a deadline must contain /by. " +
                    "\nDo format your input: deadline <task> /by <deadline>");
            return;
        }
        String[] split = text.split("/by", 2);
        ls.add(new Deadline(split[0].trim(),split[1].trim()));
    }

    public static void handlerEventTask(String input, Lists ls) {
        String text = input.substring(5).trim();
        if (text.isEmpty()) {
            printer("OOPS! The description of a event cannot be empty. " +
                    "\nDo format your input: event <task> /from <start> /to <end>");
            return;
        } else if (!input.contains("/from") || !input.contains("/to")) {
            printer("OOPS! The description of a event must contain /by. " +
                    "\nDo format your input: event <task> /from <start> /to <end>");
            return;
        }
        String[] split1 = text.split("/from", 2);
        String[] split2 = split1[1].split("/to", 2);
        ls.add(new Event(split1[0].trim(), split2[0].trim(), split2[1].trim()));
    }

    public static void handleMark(String input, Lists ls) {
        int listNum = (input.charAt(5) - '0') - 1;
        ls.mark(listNum);
        printer(markedMessage + "\n" + ls.retriveTask(listNum).toString());
    }

    public static void handleUnMark(String input, Lists ls) {
        int listNum = (input.charAt(7) - '0') - 1;
        ls.unMark(listNum);
        printer(unMarkedMessage + "\n" + ls.retriveTask(listNum).toString());
    }

    public static void run() {
        printer(welcomeMessage);
        String input = "";
        Scanner sc = new Scanner(System.in);
        Lists ls = new Lists(100);

        while(!input.toLowerCase().equals(killswitch)) {
            input = sc.nextLine();
            if (input.toLowerCase().equals(killswitch)) {
                break;
            } else if (input.toLowerCase().equals("list")) {
                printer(ls.toString());
            } else if (input.toLowerCase().startsWith("mark")) {
                handleMark(input, ls);
            } else if (input.toLowerCase().startsWith("unmark")) {
                handleUnMark(input, ls);
            } else if (input.toLowerCase().startsWith("todo")) {
                handlerTodoTask(input, ls);
            } else if (input.toLowerCase().startsWith("deadline")) {
                handlerDeadlineTask(input, ls);
            } else if (input.toLowerCase().startsWith("event")) {
                handlerEventTask(input, ls);
            } else {
                printer("Hold up! I'm sorry but I don't get what that means, please try again :-(");
            }
        }
        printer(goodbyeMessage);
    }

    public static void main(String[] args) {
        NovaGPT.run();
    }
}

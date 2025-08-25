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


    public static void run() {
        printer(welcomeMessage);
        String input = "";
        Scanner sc = new Scanner(System.in);
        Lists ls = new Lists(100);

        while(!input.toLowerCase().equals(killswitch)) {
            input = sc.nextLine();
            if (input.toLowerCase().equals(killswitch)) {
                break;
            } else if(input.toLowerCase().equals("list")) {
                printer(ls.toString());
                continue;
            } else if(input.toLowerCase().startsWith("mark")) {
                int listNum = (input.charAt(5) - '0') - 1;
                ls.mark(listNum);
                printer(markedMessage + "\n" + ls.retriveTask(listNum).toString());
                continue;
            } else if(input.toLowerCase().startsWith("unmark")) {
            int listNum = (input.charAt(7) - '0') - 1;
            ls.unMark(listNum);
            printer(unMarkedMessage + "\n" + ls.retriveTask(listNum).toString());
            continue;
            } else if(input.toLowerCase().startsWith("todo")) {
                ls.add(new Todo(input));
                continue;
            } else if(input.toLowerCase().startsWith("deadline")) {
                String text = input.substring(8);
                String[] split = text.split("/by", 2);
                ls.add(new Deadline(split[0], split[1]));
                continue;
            } else if(input.toLowerCase().startsWith("event")) {
                String text = input.substring(8);
                String[] split1 = text.split("/from", 2);
                String[] split2 = split1[1].split("/to", 2);
                ls.add(new Event(split1[0], split2[0], split2[1]));
                continue;
            }
            //ls.add(new Task(input));
            printer("invalid input");
        }

        printer(goodbyeMessage);
    }

    public static void main(String[] args) {
        NovaGPT.run();
    }
}

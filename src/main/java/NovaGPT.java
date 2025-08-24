import java.util.Scanner;

public class NovaGPT {
    private static final String welcomeMessage = "Hello, I'm NovaGPT!\nWhat can I do for you today?";
    private static final String goodbyeMessage = "Bye. Hope to see you soon!\nHAND!";
    private static final String horizontalLine = "____________________________________________________________";
    private static final String killswitch = "bye";

    public static void printer(String message) {
        System.out.print(horizontalLine + "\n" + message + "\n" + horizontalLine + "\n");
    }

    public static void run() {
        printer(welcomeMessage);
        String input = "";
        Scanner sc = new Scanner(System.in);
        List ls = new List(100);

        while(!input.toLowerCase().equals(killswitch)) {
            input = sc.nextLine();
            if (input.toLowerCase().equals(killswitch)) {
                break;
            } else if(input.toLowerCase().equals("list")) {
                //
                printer(ls.toString());
                continue;
            }
            printer("added: " + input);
            ls.add(input);
        }
        printer(goodbyeMessage);
    }

    public static void main(String[] args) {
        NovaGPT.run();
    }
}

package NovaGPT.ui;

import NovaGPT.command.Command;
import NovaGPT.command.Parser;
import NovaGPT.command.TaskList;
import NovaGPT.exception.NovaException;
import NovaGPT.task.Task;
import NovaGPT.storage.Storage;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The NovaGPT program implements a chatbot application that
 * interacts with a user with a pre-defined list of instructions.
 *
 * @author  Balakrishnan Naveen Mani Kumar
 * @version 0.1
 * @since   2025-08-12
 */
public class NovaGPT {
    private static final String WELCOME_MESSAGE =
            " _   _                  ____ ____ _____ \n" +
            "| \\ | | _____   ____ _ / ___|  _ \\_   _| \n" +
            "|  \\| |/ _ \\ \\ / / _` | |  _| |_) || |  \n" +
            "| |\\  | (_) \\ V / (_| | |_| |  __/ | |   \n" +
            "|_| \\_|\\___/ \\_/ \\__,_|\\____|_|    |_|\n" +
            "\n" +
            "Hello! What can I do for you today?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you soon!\nHAND!";
    private static final String KILL_SWITCH = "bye";
    private static final String LIST_OF_COMMANDS =
            """
                    List of valid commands:\s
                    todo <task name>\s
                    deadline <task name> /by <deadline>\s
                    event <task name> /from <start time> /to <end time>\s
                    find <Task number>\s
                    delete <Task number>\s
                    mark <Task number>\s
                    unmark <Task number>\s
                    list\s
                    Bye""";

    /**
     * Starts the main program loop.
     * Continuously read for user input, parse the input into commands,
     * executes the commands and its relevant actions and provide the output through the UI.
     * Continues until the exit command is released through the input "bye".
     *
     * @param filePath This is the filePath in which the application stores its list of tasks.
     * @return Nothing.
     */
    public static void run(String filePath) {
        Ui.print(WELCOME_MESSAGE);
        String input = "";
        Scanner sc = new Scanner(System.in);
        Storage st = new Storage(filePath);
        ArrayList<Task> ls = st.load();

        while (!input.toLowerCase().equals(KILL_SWITCH)) {
            input = sc.nextLine();
            Command command = Parser.parseCommandFromInput(input);
            try {
                switch (command) {
                case BYE:
                    break;
                case LIST:
                    Ui.print(TaskList.handleList(ls));
                    break;
                case MARK:
                    TaskList.handleMark(input, ls, st);
                    break;
                case UNMARK:
                    TaskList.handleUnMark(input, ls, st);
                    break;
                case TODO:
                    TaskList.handleTodoTask(input, ls, st);
                    break;
                case DEADLINE:
                    TaskList.handleDeadlineTask(input, ls, st);
                    break;
                case EVENT:
                    TaskList.handleEventTask(input, ls, st);
                    break;
                case DELETE:
                    TaskList.handleDelete(input, ls, st);
                    break;
                case FIND:
                    TaskList.handleFind(input, ls, st);
                    break;
                case MAN:
                    Ui.print(LIST_OF_COMMANDS);
                    break;
                case UNKNOWN:
                    throw new NovaException("Hold up! I'm sorry! \n" +
                            "I don't get what that means, please try again :-( \n" +
                            "Prompt 'man' for help"
                            );
                }
            } catch (NovaException e) {
                    Ui.print(e.getMessage());
            } catch (Exception e) {
                    Ui.print("Unexpected error: " + e.getMessage());
            }
        }
        Ui.print(GOODBYE_MESSAGE);
    }

    /**
     * This is the main method which calls the run method.
     * @param args Unused.
     */
    public static void main(String[] args) {
        NovaGPT.run("./data/NovaGPT.txt");
    }
}

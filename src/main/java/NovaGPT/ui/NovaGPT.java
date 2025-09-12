package novagpt.ui;

import java.util.ArrayList;
import java.util.Scanner;

import novagpt.command.Command;
import novagpt.command.Parser;
import novagpt.command.TaskList;
import novagpt.exception.NovaException;
import novagpt.storage.Storage;
import novagpt.task.Task;


/**
 * The NovaGPT program implements a chatbot application that
 * interacts with a user with a pre-defined list of instructions.
 *
 * @author  Balakrishnan Naveen Mani Kumar
 * @version 0.1
 * @since   2025-08-12
 */

public class NovaGpt {
    /**
     * Starts the main program loop.
     * Continuously read for user input, parse the input into commands,
     * executes the commands and its relevant actions and provide the output through the UI.
     * Continues until the exit command is released through the input "bye".
     *
     * @param filePath This is the filePath in which the application stores its list of tasks.
     */
    public static void run(String filePath) {
        Ui.welcomeMessage();
        String input = "";
        Scanner sc = new Scanner(System.in);
        Storage st = new Storage(filePath);
        ArrayList<Task> ls = st.load();

        while (!input.toLowerCase().equals(Ui.KILL_SWITCH)) {
            input = sc.nextLine();
            Command command = Parser.parseCommandFromInput(input);
            try {
                switch (command) {
                case BYE:
                    break;
                case LIST:
                    TaskList.handleList(ls);
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
                    Ui.print(Ui.LIST_OF_COMMANDS);
                    break;
                case UNKNOWN:
                    throw new NovaException("Hold up! I'm sorry! \n"
                            + "I don't get what that means, please try again :-( \n"
                            + "Prompt 'man' for help"
                            );
                default:
                    throw new NovaException("Error, try again");
                }
            } catch (NovaException e) {
                Ui.errorMessage(e.getMessage());
            } catch (Exception e) {
                Ui.unexpectedErrorMessage(e.getMessage());
            }
        }
        Ui.goodbyeMessage();
    }

    /**
     * This is the main method which calls the run method.
     * @param args Unused.
     */
    public static void main(String[] args) {
        NovaGpt.run("./data/NovaGPT.txt");
    }
}

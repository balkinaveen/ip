package novagpt.ui;

import java.util.ArrayList;

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

    private final Storage storage;
    private final ArrayList<Task> tasks;
    /**
     * Initializes a chatbot instance
     * @param filePath is where the file is stored
     */
    public NovaGpt(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }
    /**
     * Parses the input and gives the output
     * @param input is the input provided by user
     */
    public String response(String input) {
        Command command = Parser.parseCommandFromInput(input);
        try {
            switch (command) {
            case BYE:
                break;
            case LIST:
                return TaskList.handleList(tasks);
            case MARK:
                return TaskList.handleMark(input, tasks, storage);
            case UNMARK:
                return TaskList.handleUnMark(input, tasks, storage);
            case TODO:
                return TaskList.handleTodoTask(input, tasks, storage);
            case DEADLINE:
                return TaskList.handleDeadlineTask(input, tasks, storage);
            case EVENT:
                return TaskList.handleEventTask(input, tasks, storage);
            case DELETE:
                return TaskList.handleDelete(input, tasks, storage);
            case FIND:
                return TaskList.handleFind(input, tasks, storage);
            case MAN:
                return Ui.print(Ui.LIST_OF_COMMANDS);
            case UNKNOWN:
                throw new NovaException("Hold up! I'm sorry! \n"
                        + "I don't get what that means, please try again :-( \n"
                        + "Prompt 'man' for help"
                        );
            default:
                throw new NovaException("Error, try again");
            }
        } catch (NovaException e) {
            return Ui.errorMessage(e.getMessage());
        } catch (Exception e) {
            return Ui.unexpectedErrorMessage(e.getMessage());
        }
        return null;
    }
}

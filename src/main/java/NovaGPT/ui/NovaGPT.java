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

    private static final String UNKNOWN_COMM_MESSAGE = """
            Hold up! I'm sorry!
            I don't get what that means, please try again :-(
            Prompt 'man' for help""";
    private static final String GENERIC_COMM_MESSAGE = "An unexpected error has occurred. Please try again!";
    private final Storage storage;
    private final ArrayList<Task> tasks;
    /**
     * Initializes a chatbot instance
     * @param filePath is where the file is stored
     */
    public NovaGpt(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        assert storage != null : "Storage must not be null";
        assert tasks != null : "Tasks list must be null after loading";
    }
    /**
     * Parses the input and gives the output
     * @param input is the input provided by user
     * @return Response message (output) for the user
     */
    public String response(String input) {
        assert input != null : "Input string must not be null";
        Command command = Parser.parseCommandFromInput(input);
        assert command != null : "Parsed command must not be null";
        try {
            return handleCommand(command, input);
        } catch (NovaException e) {
            return Ui.errorMessage(e.getMessage());
        } catch (Exception e) {
            return Ui.unexpectedErrorMessage(e.getMessage());
        }
    }
    /**
     * Handles the given command and returns the appropriate response
     * @param command Parsed command type
     * @param input is the input provided by user
     * @return Response message (output) for the user
     * @throws NovaException if an invalid command is given
     */
    public String handleCommand(Command command, String input) throws NovaException {
        switch (command) {
        case BYE:
            break;
        case LIST:
            return TaskList.handleList(tasks);
        case MARK:
            return TaskList.handleMark(input, tasks, storage);
        case UNMARK:
            return TaskList.handleUnmark(input, tasks, storage);
        case TODO:
            return TaskList.handleTodoTask(input, tasks, storage);
        case DEADLINE:
            return TaskList.handleDeadlineTask(input, tasks, storage);
        case EVENT:
            return TaskList.handleEventTask(input, tasks, storage);
        case DELETE:
            return TaskList.handleDelete(input, tasks, storage);
        case FIND:
            return TaskList.handleFind(input, tasks);
        case MAN:
            return Ui.LIST_OF_COMMANDS;
        case UNKNOWN:
            throw new NovaException(UNKNOWN_COMM_MESSAGE);
        default:
            throw new NovaException(GENERIC_COMM_MESSAGE);
        }
        throw new NovaException(GENERIC_COMM_MESSAGE);
    }
}

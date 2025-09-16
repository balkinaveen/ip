package novagpt.ui;

import java.util.ArrayList;

import novagpt.command.Command;
import novagpt.command.Parser;
import novagpt.command.TaskList;
import novagpt.exception.NovaException;
import novagpt.storage.Storage;
import novagpt.task.Task;


/**
 * The {@code NovaGpt} class represents the main logic handler of the NovaGPT chatbot.
 * It interprets user input, delegates the execution of commands, and interacts with
 * persistent storage.
 *
 * <p>This class loads tasks from disk, processes user commands,
 * and returns responses based on user interaction.</p>
 *
 * <p>It relies on {@link Storage} for loading and saving tasks,
 * and uses {@link Parser} and {@link TaskList} to handle command parsing
 * and execution logic.</p>
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
     * Constructs a new {@code NovaGpt} instance with the specified file path for persistent storage.
     * It loads any previously saved tasks from disk into memory.
     *
     * @param filePath The path to the file used for reading and writing task data.
     * @throws AssertionError if the storage or loaded task list is null.
     */
    public NovaGpt(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        assert storage != null : "Storage must not be null";
        assert tasks != null : "Tasks list must be null after loading";
    }
    /**
     * Processes the given user input and returns the chatbot's textual response.
     * The input is first parsed into a {@link Command}, which is then executed.
     *
     * @param input The user's input string.
     * @return A string representing NovaGPT's response to the user.
     * @throws AssertionError if the input or parsed command is null.
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
     * Executes the given {@link Command} based on the user's input,
     * and returns the appropriate output string for the chatbot.
     *
     * @param command The parsed command to execute.
     * @param input   The original user input string.
     * @return A string representing the result of executing the command.
     * @throws NovaException If the command is invalid or improperly formatted.
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
        case REMINDER:
            return TaskList.handleReminders(input, tasks);
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

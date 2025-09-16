package novagpt.command;

import novagpt.exception.NovaException;

/**
 * Represents a Parser class, entails all the parsing methods
 * related to user inputs.
 * Extracts commands and integers from user inputs
 */
public class Parser {
    private static final String KILL_SWITCH = "bye";

    /**
     * Returns the respective command based on user inputs
     *
     * @param input The input string that the user provides
     * @return Command
     */
    public static Command parseCommandFromInput(String input) {
        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.equals(KILL_SWITCH)) {
            return Command.BYE;
        } else if (lowerCaseInput.equals("list")) {
            return Command.LIST;
        } else if (lowerCaseInput.startsWith("mark")) {
            return Command.MARK;
        } else if (lowerCaseInput.startsWith("unmark")) {
            return Command.UNMARK;
        } else if (lowerCaseInput.startsWith("todo")) {
            return Command.TODO;
        } else if (lowerCaseInput.startsWith("deadline")) {
            return Command.DEADLINE;
        } else if (lowerCaseInput.startsWith("event")) {
            return Command.EVENT;
        } else if (lowerCaseInput.startsWith("delete")) {
            return Command.DELETE;
        } else if (lowerCaseInput.startsWith("find")) {
            return Command.FIND;
        } else if (lowerCaseInput.startsWith("reminder")) {
            return Command.REMINDER;
        } else if (lowerCaseInput.startsWith("man")) {
            return Command.MAN;
        } else {
            return Command.UNKNOWN;
        }
    }

    /**
     * Parses the task index from a command input string
     * The index returned is zero-indexed.
     *
     * @param input string that the user provides
     * @param command command keyword tied to the user input
     * @return Zero-indexed task index
     * @throws NovaException if input does not contain a valid number
     */
    public static int parseTaskIndex(String input, String command) throws NovaException {
        try {
            String numberString = input.substring(command.length()).trim();
            int index = Integer.parseInt(numberString) - 1;
            if (index < 0) {
                throw new NovaException("OOPS! The task number must be positive!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new NovaException("OOPS! Please enter a valid number! "
                    + "\nDo format your input: "
                    + command
                    + " <number>");
        }
    }

}

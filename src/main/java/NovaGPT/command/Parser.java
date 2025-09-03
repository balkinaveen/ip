package NovaGPT.command;

import NovaGPT.exception.NovaException;

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
        if (lowerCaseInput.equals(KILL_SWITCH)) return Command.BYE;
        if (lowerCaseInput.equals("list")) return Command.LIST;
        if (lowerCaseInput.startsWith("mark")) return Command.MARK;
        if (lowerCaseInput.startsWith("unmark")) return Command.UNMARK;
        if (lowerCaseInput.startsWith("todo")) return Command.TODO;
        if (lowerCaseInput.startsWith("deadline")) return Command.DEADLINE;
        if (lowerCaseInput.startsWith("event")) return Command.EVENT;
        if (lowerCaseInput.startsWith("delete")) return Command.DELETE;
        if (lowerCaseInput.startsWith("find")) return Command.FIND;
        return Command.UNKNOWN;
    }

    /**
     * Returns the specified item as a zero-indexed integer
     * If the input is not valid, throws an error
     *
     * @param input The input string that the user provides
     * @param command The command string tied to the user input
     * @return Zero-indexed integer
     * @throws NovaException if input is invalid
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

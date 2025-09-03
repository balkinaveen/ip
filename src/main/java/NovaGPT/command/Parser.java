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
     * Takes in an input String and uses an "if" logic to check the start of
     * input and returns the respective command
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
        return Command.UNKNOWN;
    }

    /**
     * Takes in an input String and a command String, extracts the
     * number from the input and returns it as a zero-indexed integer
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
            throw new NovaException("OOPS! Please enter a valid number! \nDo format your input: " + command + " <number>");
        }
    }

}

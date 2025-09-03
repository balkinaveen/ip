package NovaGPT.command;

import NovaGPT.exception.NovaException;

public class Parser {
    private static final String KILL_SWITCH = "bye";

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

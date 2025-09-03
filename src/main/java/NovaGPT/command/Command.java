package NovaGPT.command;

/**
 * Represents the different commands that the chatbot supports
 * This enum provides a fixed set of commands for the chatbot,
 * ensuring type safety while working with command-related operations
 */

public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    FIND,
    UNKNOWN
}

package NovaGPT.command;

import NovaGPT.exception.NovaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parseCommandFromInput_bye_returnsBye() {
        assertEquals(Command.BYE, Parser.parseCommandFromInput("bye"));
        assertEquals(Command.BYE, Parser.parseCommandFromInput("bYe"));
        assertEquals(Command.BYE, Parser.parseCommandFromInput("byE"));
        assertEquals(Command.BYE, Parser.parseCommandFromInput("ByE"));
        assertEquals(Command.BYE, Parser.parseCommandFromInput("BYe"));
        assertEquals(Command.BYE, Parser.parseCommandFromInput("BYE"));
    }

    @Test
    public void parseCommandFromInput_list_returnsList() {
        assertEquals(Command.LIST, Parser.parseCommandFromInput("list"));
        assertEquals(Command.LIST, Parser.parseCommandFromInput("lISt"));
        assertEquals(Command.LIST, Parser.parseCommandFromInput("LIST"));
    }

    @Test
    public void parseCommandFromInput_mark_returnsMark() {
        assertEquals(Command.MARK, Parser.parseCommandFromInput("mark 1"));
        assertEquals(Command.MARK, Parser.parseCommandFromInput("mARk 1"));
        assertEquals(Command.MARK, Parser.parseCommandFromInput("MARK 1"));
    }

    @Test
    public void parseCommandFromInput_unmark_returnsUnmark() {
        assertEquals(Command.UNMARK, Parser.parseCommandFromInput("unmark 1"));
        assertEquals(Command.UNMARK, Parser.parseCommandFromInput("uNmARk 1"));
        assertEquals(Command.UNMARK, Parser.parseCommandFromInput("UNMARK 1"));
    }

    @Test
    public void parseCommandFromInput_todo_returnsTodo() {
        assertEquals(Command.TODO, Parser.parseCommandFromInput("todo read book"));
        assertEquals(Command.TODO, Parser.parseCommandFromInput("ToDo read book"));
    }

    @Test
    public void parseCommandFromInput_deadline_returnsDeadline() {
        assertEquals(Command.DEADLINE, Parser.parseCommandFromInput("deadline read book /by 05/11/2013 1900"));
        assertEquals(Command.DEADLINE, Parser.parseCommandFromInput("DEADLINE read book"));
    }

    @Test
    public void parseCommandFromInput_event_returnsEvent() {
        assertEquals(Command.EVENT, Parser.parseCommandFromInput("event read book /from 05/11/2013 1900 /to 05/11/2013 1900"));
        assertEquals(Command.EVENT, Parser.parseCommandFromInput("EVENT read book /from 05/11/2013 1900 /to 05/11/2013 1900"));
    }

    @Test
    public void parseCommandFromInput_delete_returnsDelete() {
        assertEquals(Command.DELETE, Parser.parseCommandFromInput("delete 1"));
        assertEquals(Command.DELETE, Parser.parseCommandFromInput("dElEte 1"));
        assertEquals(Command.DELETE, Parser.parseCommandFromInput("DELETE 1"));
    }

    @Test
    public void parseCommandFromInput_unknown_returnsUnknown() {
        assertEquals(Command.UNKNOWN, Parser.parseCommandFromInput("undo"));
    }

    @Test
    public void parseTaskIndex_validNumber_returnsIndexZeroBased() throws NovaException {
        assertEquals(4,Parser.parseTaskIndex("mark 5", "mark"));
        assertEquals(9,Parser.parseTaskIndex("delete 10", "delete"));
        assertEquals(1,Parser.parseTaskIndex("unmark 2", "unmark"));
    }

    @Test
    public void parseTaskIndex_negativeNumber_throwsException() throws NovaException {
        NovaException e = assertThrows(NovaException.class, () -> Parser.parseTaskIndex("mark -5", "mark"));
        assertTrue(e.getMessage().contains("must be positive"));
    }

    @Test
    public void parseTaskIndex_invalidInput_throwsException() throws NovaException {
        NovaException e = assertThrows(NovaException.class, () -> Parser.parseTaskIndex("mark five", "mark"));
        assertTrue(e.getMessage().contains("enter a valid number"));
    }

    @Test
    public void parseTaskIndex_emptyInput_throwsException() throws NovaException {
        NovaException e = assertThrows(NovaException.class, () -> Parser.parseTaskIndex("mark  ", "mark"));
        assertTrue(e.getMessage().contains("enter a valid number"));
    }
}

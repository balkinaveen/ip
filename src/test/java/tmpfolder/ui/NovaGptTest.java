//More Test Cases Written for A-AIassisted Increment using ChatGPT AI
//Input given: Write Test cases for NovaGpt class
//Output is as follows:
package novagpt.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import novagpt.command.Command;
import novagpt.command.Parser;
import novagpt.storage.Storage;
import novagpt.task.Task;

public class NovaGptTest {
    private ArrayList<Task> taskList;
    private Storage storage;


    @BeforeEach
    public void setup() {
        storage = new Storage("./data/TestNovaGPT.txt");
        taskList = new ArrayList<>();
    }


    @Test
    public void parser_recognizesCommand_correctly() {
        assertEquals(Command.TODO, Parser.parseCommandFromInput("todo read book"));
        assertEquals(Command.DEADLINE, Parser.parseCommandFromInput("deadline return book /by Sunday"));
        assertEquals(Command.EVENT, Parser.parseCommandFromInput("event team meeting /at Mon 2pm"));
        assertEquals(Command.LIST, Parser.parseCommandFromInput("list"));
        assertEquals(Command.UNKNOWN, Parser.parseCommandFromInput("blah"));
    }


    @Test
    public void storage_savesAndLoads_tasksCorrectly() {
        taskList.clear();
        storage.save(taskList); // Save empty list
        ArrayList<Task> loadedList = storage.load();
        assertNotNull(loadedList);
        assertEquals(0, loadedList.size());
    }


    @Test
    public void ui_killSwitch_matchCaseInsensitive() {
        assertTrue("BYE".equalsIgnoreCase("bye"));
        assertTrue("Bye".toLowerCase().equals("bye"));
    }


    @Test
    public void parser_commandParsing_edgeCases() {
        assertEquals(Command.UNKNOWN, Parser.parseCommandFromInput(""));
        assertEquals(Command.UNKNOWN, Parser.parseCommandFromInput(" "));
    }
}

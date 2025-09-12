package novagpt.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import novagpt.exception.NovaException;
import novagpt.storage.Storage;
import novagpt.task.Deadline;
import novagpt.task.Task;
import novagpt.task.Todo;


public class TaskListTest {

    private ArrayList<Task> ls;
    private Storage st;

    @BeforeEach
    public void setUp() {
        ls = new ArrayList<>();
        st = new Storage("mock.txt");
    }

    @Test
    public void handleTodoTask_validInput_addsTask() throws NovaException {
        TaskList.handleTodoTask("todo read book", ls, st);
        assertEquals(1, ls.size());
        assertTrue(ls.get(0) instanceof Todo);
        assertEquals("[T][ ] read book", ls.get(0).toString());

    }

    @Test
    public void handleTodoTask_invalidInput_noTask() throws NovaException {
        assertThrows(NovaException.class, () -> TaskList.handleTodoTask("todo ", ls, st));

    }

    @Test
    public void handleDeadlineTask_validInput_addsTask() throws NovaException {
        TaskList.handleDeadlineTask("deadline read book /by 05/11/2013 1900 ", ls, st);
        assertEquals(1, ls.size());
        assertTrue(ls.get(0) instanceof Deadline);
    }

    @Test
    public void handleDeadlineTask_invalidInput_noTask() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleDeadlineTask("deadline /by 05/11/2013 1900 ", ls, st));
    }

    @Test
    public void handleDeadlineTask_wrongDateAndTimeFormat_throwsException() throws NovaException {
        assertThrows(NovaException.class, (
                ) -> TaskList.handleDeadlineTask("deadline read book /by 05/11/2013 19:00 ", ls, st));
    }

    @Test
    public void handleDeadlineTask_noTime_throwsException() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleDeadlineTask("deadline read book /by 05/11/2013 ", ls, st));
    }

    @Test
    public void handleDeadlineTask_noDateAndTime_noTask() throws NovaException {
        assertThrows(NovaException.class, (
                ) -> TaskList.handleDeadlineTask("deadline read book ", ls, st));

    }

    @Test
    public void handleEventTask_valid_addsTask() throws NovaException {
        TaskList.handleEventTask("event read book /from 22/06/2003 1200 /to 05/11/2013 1200", ls, st);
        assertEquals(1, ls.size());
    }

    @Test
    public void handleEventTask_noTo_noTask() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleEventTask("event read book /from 22/06/2003 1200 ", ls, st));

    }

    @Test
    public void handleEventTask_noFrom_noTask() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleEventTask("event read book /to 05/11/2013 1200", ls, st));

    }

    @Test
    public void handleEventTask_noFromTo_noTask() throws NovaException {

        assertThrows(NovaException.class, (
                ) -> TaskList.handleEventTask("event read book ", ls, st));
    }

    @Test
    public void handleEventTask_noTime_throwsException() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleEventTask("event read book /from 22/06/2003 /to 05/11/2013", ls, st));
    }

    @Test
    public void handleEventTask_invalidInput_noAdd() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleEventTask("event /from 22/06/2003 1200 /to 05/11/2013 1200", ls, st));

    }

    @Test
    void handleMark_validIndex_marksTask() throws NovaException {
        Task t = new Todo("read book");
        ls.add(t);
        TaskList.handleMark("mark 1", ls, st);
        assertTrue(t.getStatus());
    }

    @Test
    void handleMark_outOfRange_throwsException() throws NovaException {
        Task t = new Todo("read book");
        ls.add(t);
        assertThrows(NovaException.class, (
        ) -> TaskList.handleMark("mark 2", ls, st));
    }

    @Test
    void handleUnmark_validIndex_marksTask() throws NovaException {
        Task t = new Todo("read book");
        ls.add(t);
        TaskList.handleMark("mark 1", ls, st);
        TaskList.handleUnMark("unmark 1", ls, st);
        assertFalse(t.getStatus());
    }

    @Test
    void handleUnmark_outOfRange_throwsException() throws NovaException {
        Task t = new Todo("read book");
        ls.add(t);
        assertThrows(NovaException.class, (
        ) -> TaskList.handleUnMark("unmark 2", ls, st));
    }

    @Test
    void handleDelete_validIndex_removesTask() throws NovaException {
        Task t = new Todo("read book");
        ls.add(t);
        assertEquals(1, ls.size());
        TaskList.handleDelete("delete 1", ls, st);
        assertTrue(ls.isEmpty());
    }
    @Test
    void handleDelete_outOfRange_throwsException() throws NovaException {
        assertThrows(NovaException.class, (
        ) -> TaskList.handleDelete("delete 1", ls, st));
    }
}

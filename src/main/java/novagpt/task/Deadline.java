package novagpt.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import novagpt.exception.NovaException;

/**
 * Represents a {@code Deadline} task.
 * A deadline task contains a description and a due date/time.
 * Inputs are expected in the format {@code dd/MM/yyyy HHmm}, and are reformatted for output.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private final LocalDateTime endTimeDate;

    /**
     * Constructs a {@code Deadline} task.
     *
     * @param description Description of the deadline.
     * @param deadline Due datetime string (format: {@code dd/MM/yyyy HHmm}).
     * @throws NovaException If the input datetime format is incorrect.
     */
    public Deadline(String description, String deadline) throws NovaException {
        super(description);
        try {
            this.endTimeDate = LocalDateTime.parse(deadline, INPUT_FORMAT);
        } catch (DateTimeException e) {
            throw new NovaException("OOPS! Wrong format, please key in date and time in "
                    + INPUT_FORMAT
                    + " (24 hour) format");
        }
    }

    /**
     * Returns the deadline's due date and time.
     *
     * @return {@code LocalDateTime} object for the due datetime.
     */
    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeDate;
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return A formatted string with task type, status, description, and due date/time.
     */
    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + endTimeDate.format(OUTPUT_FORMAT)
                + ")";
    }
}

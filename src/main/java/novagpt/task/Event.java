package novagpt.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import novagpt.exception.NovaException;

/**
 * Represents an {@code Event} task.
 * An event task contains a description, a start date/time, and an end date/time.
 * Inputs are expected in the format {@code dd/MM/yyyy HHmm}, and are reformatted for output.
 *
 * <p>
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private final LocalDateTime startTimeDate;
    private final LocalDateTime endTimeDate;

    /**
     * Constructs an {@code Event} task.
     *
     * @param description Description of the event.
     * @param startTimeDate Start datetime string (format: {@code dd/MM/yyyy HHmm}).
     * @param endTimeDate End datetime string (format: {@code dd/MM/yyyy HHmm}).
     * @throws NovaException If the input datetime format is incorrect.
     */
    public Event(String description, String startTimeDate, String endTimeDate) throws NovaException {
        super(description);
        try {
            this.startTimeDate = LocalDateTime.parse(startTimeDate, INPUT_FORMAT);
            this.endTimeDate = LocalDateTime.parse(endTimeDate, INPUT_FORMAT);
        } catch (DateTimeException e) {
            throw new NovaException("OOPS! Wrong format, please key in date and time in "
                    + INPUT_FORMAT
                    + " (24 hour) format");
        }
    }

    /**
     * Returns the event start date and time.
     *
     * @return {@code LocalDateTime} object for the start datetime.
     */
    public LocalDateTime getStartTimeAndDate() {
        return this.startTimeDate;
    }

    /**
     * Returns the event end date and time.
     *
     * @return {@code LocalDateTime} object for the end datetime.
     */
    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeDate;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return A formatted string with task type, status, description, and time range.
     */
    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + this.startTimeDate.format(OUTPUT_FORMAT)
                + " to: "
                + this.endTimeDate.format(OUTPUT_FORMAT)
                + ")";
    }
}

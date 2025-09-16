package novagpt.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import novagpt.exception.NovaException;

/**
 * Represents an Event task
 * Each task has a description, a completion status,
 * a start Time and Date and an end Time and Date.
 * Takes in a specific format of Date and Time and
 * outputs a set format of Date and Time
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private final LocalDateTime startTimeDate;
    private final LocalDateTime endTimeDate;

    /**
     * Constructs an event object with the given description and start/end date and time
     * @param description of the event
     * @param startTimeDate Start date-time in string
     * @param endTimeDate End date-time in string
     * @throws NovaException if either date or time does not follow the expected format
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
     * Gets the start time and date
     */
    public LocalDateTime getStartTimeAndDate() {
        return this.startTimeDate;
    }

    /**
     * Gets the end time and date
     */
    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeDate;
    }

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

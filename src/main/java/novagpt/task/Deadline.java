package novagpt.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import novagpt.exception.NovaException;

/**
 * Represents a Deadline task
 * Each task has a description, a completion status, an end Time and Date.
 * Takes in a specific format of Date and Time and
 * outputs a set format of Date and Time
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private final LocalDateTime endTimeDate;

    /**
     * Constructs a deadline object with the given description and end date and time
     * @param description of the deadline
     * @param deadline End date-time in string
     * @throws NovaException if either date or time does not follow the expected format
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
     * Returns the end time and date
     */
    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeDate;
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + endTimeDate.format(OUTPUT_FORMAT)
                + ")";
    }
}

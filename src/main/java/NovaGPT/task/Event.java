package NovaGPT.task;

import NovaGPT.exception.NovaException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task
 * Each task has a description, a completion status,
 * a start Time and Date and an end Time and Date.
 * Takes in a specific format of Date and Time and
 * outputs a set format of Date and Time
 */
public class Event extends Task{
    private LocalDateTime startTimeAndDate;
    private LocalDateTime endTimeAndDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.
            ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.
            ofPattern("MMM dd yyyy, h:mma");

    public Event(String description, String startTimeAndDate, String endTimeAndDate) throws NovaException{
        super(description);
        try {
            this.startTimeAndDate = LocalDateTime.parse(startTimeAndDate, INPUT_FORMAT);
            this.endTimeAndDate = LocalDateTime.parse(endTimeAndDate, INPUT_FORMAT);
        } catch (DateTimeException e) {
            throw new NovaException("OOPS! Wrong format, please key in date and time in DD/MM/YYYY HHMM " +
                    "(24 hour) format");
        }
    }

    public Event(String description, LocalDateTime startTimeAndDate, LocalDateTime endTimeAndDate) {
        super(description);
        this.startTimeAndDate = startTimeAndDate;
        this.endTimeAndDate = endTimeAndDate;
    }

    /**
     * Gets the start time and date
     */
    public LocalDateTime getStartTimeAndDate() {
        return this.startTimeAndDate;
    }

    /**
     * Gets the end time and date
     */
    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeAndDate;
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + this.startTimeAndDate.format(OUTPUT_FORMAT)
                + " to: "
                + this.endTimeAndDate.format(OUTPUT_FORMAT)
                + ")";
    }
}

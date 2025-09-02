package NovaGPT.task;

import NovaGPT.exception.NovaException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDateTime endTimeAndDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public Deadline(String description, String deadline) throws NovaException{
        super(description);
        try {
            this.endTimeAndDate = LocalDateTime.parse(deadline, INPUT_FORMAT);
        } catch (DateTimeException e) {
            throw new NovaException("OOPS! Wrong format, please key in date and time in DD/MM/YYYY HHMM (24 hour) format");
        }
    }

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.endTimeAndDate = deadline;
    }

    public LocalDateTime getEndTimeAndDate() {
        return this.endTimeAndDate;
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + endTimeAndDate.format(OUTPUT_FORMAT)
                + ")";
    }
}

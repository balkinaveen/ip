public class Event extends Task{
    String startTimeAndDate;
    String endTimeAndDate;

    public Event(String description, String startTimeAndDate, String endTimeAndDate) {
        super(description);
        this.startTimeAndDate = startTimeAndDate;
        this.endTimeAndDate = endTimeAndDate;
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + this.startTimeAndDate
                + " to: "
                + this.endTimeAndDate + ")";
    }
}

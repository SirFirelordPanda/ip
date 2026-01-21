public class Event extends Task{

    String fromWhen;
    String toWhen;

    public Event(String taskMsg, String fromWhen, String toWhen) {
        super(taskMsg);
        this.fromWhen = fromWhen;
        this.toWhen = toWhen;
    }

    @Override
    public String toString() {
        String eventTime = String.format(" (from: %s to: %s)", fromWhen, toWhen);
        return "[E]" + super.toString() + eventTime;
    }
}

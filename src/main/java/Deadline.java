public class Deadline extends Task{

    public String byWhen;

    public Deadline(String taskMsg, String byWhen){
        super(taskMsg);
        this.byWhen = byWhen;
    }

    @Override
    public String toString() {
        String deadline = String.format(" (by: %s)", byWhen);
        return "[D]" + super.toString() + deadline;
    }
}

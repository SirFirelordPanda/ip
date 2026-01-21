public class ToDo extends Task{

    public ToDo(String taskMsg) {
        super(taskMsg);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

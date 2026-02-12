package misora.tasks;

public enum Priority {
    LOW("L", 1),
    MEDIUM("M", 2),
    HIGH("H", 3);

    private final String displayName;
    private final int weight;

    Priority(String displayName, int weight) {
        this.displayName = displayName;
        this.weight = weight;
    }

    public int getWeight() { return weight; }

    @Override
    public String toString() {
        return displayName;
    }
}
package misora.tasks;

/**
 * Represents the priority level of a task.
 *
 * <p>Each priority level has:
 * <ul>
 *     <li>A short display name used for user-facing output.</li>
 *     <li>A numerical weight used for comparison or sorting.</li>
 * </ul>
 *
 * <p>The available priority levels are:
 * <ul>
 *     <li>{@link #LOW}</li>
 *     <li>{@link #MEDIUM}</li>
 *     <li>{@link #HIGH}</li>
 * </ul>
 *
 * <p>The weight increases with priority severity, where
 * {@code HIGH} has the largest weight.
 */
public enum Priority {

    /** Lowest priority level. */
    LOW("L", 1),

    /** Default priority level. */
    MEDIUM("M", 2),

    /** Highest priority level. */
    HIGH("H", 3);

    /** Short label used when displaying the priority (e.g., "L", "M", "H"). */
    private final String displayName;

    /** Numerical value representing the importance of the priority. */
    private final int weight;

    /**
     * Constructs a {@code Priority} with a display label and weight.
     *
     * @param displayName The short label used for display purposes.
     * @param weight The numerical weight representing priority severity.
     */
    Priority(String displayName, int weight) {
        this.displayName = displayName;
        this.weight = weight;
    }

    /**
     * Returns the numerical weight of this priority.
     *
     * <p>Higher values indicate higher priority.
     *
     * @return The priority weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the display label of this priority.
     *
     * @return The short display name (e.g., "L", "M", "H").
     */
    @Override
    public String toString() {
        return displayName;
    }
}

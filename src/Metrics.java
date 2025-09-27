package src;

public class Metrics {
    private long comparisons = 0;
    private long swaps = 0;
    private long allocations = 0;

    private int currentDepth = 0;
    private int maxDepth = 0;

    private long startTime = 0;
    private long elapsedNanos = 0;

    public void addComparison() { comparisons++; }
    public void addComparisons(long v) { comparisons += v; }
    public long getComparisons() { return comparisons; }

    public void addSwap() { swaps++; }
    public long getSwaps() { return swaps; }

    public void addAllocation() { allocations++; }
    public long getAllocations() { return allocations; }

    public void pushDepth() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public void popDepth() {
        if (currentDepth > 0) currentDepth--;
    }
    public int getMaxDepth() { return maxDepth; }

    public void startTimer() { startTime = System.nanoTime(); }
    public void stopTimer() { elapsedNanos = System.nanoTime() - startTime; }
    public double getElapsedMillis() { return elapsedNanos / 1_000_000.0; }
}

package os.process;

/**
 * @author swarfte
 */
public class Processes {
    private final String name;
    private final int arrivalTime;
    private int burstTime;
    private final int priority;

    public Processes(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = 0;
    }

    public Processes(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isFinished() {
        return this.burstTime == 0;
    }


    @Override
    public String toString() {
        return this.name;
    }
}

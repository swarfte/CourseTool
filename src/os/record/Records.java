package os.record;

import java.util.List;

import os.process.Processes;

/**
 * @author swarfte
 */

public class Records {
    private int processesTime;
    private String processesName;
    private List<Processes> processesQueue;

    public Records() {

    }

    public Records(int processesTime, String processesName, List<Processes> processesQueue) {
        this.processesTime = processesTime;
        this.processesName = processesName;
        this.processesQueue = List.copyOf(processesQueue);
    }

    public double getProcessesTime() {
        return this.processesTime;
    }

    public String getProcessesName() {
        return this.processesName;
    }

    public List<Processes> getProcessesQueue() {
        return this.processesQueue;
    }


    @Override
    public String toString() {
        return this.processesTime + " " + this.processesName + " " + this.processesQueue.toString();
    }
}

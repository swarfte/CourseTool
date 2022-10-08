package operating_system_old;

import java.util.ArrayList;

class ProcessLog {
    protected final Integer currentTime;
    protected final String currentProcess;
    protected final ArrayList<String> processQueue;

    public ProcessLog(Integer currentTime, String currentProcess, ArrayList<String> processQueue) {
        this.currentTime = currentTime;
        this.currentProcess = currentProcess;
        this.processQueue = (ArrayList<String>) processQueue.clone();
    }

    @Override
    public String toString() {

        return "" +
                "" + currentTime +
                ", '" + currentProcess + '\'' +
                ", " + processQueue.toString() +
                "\n";
    }
}

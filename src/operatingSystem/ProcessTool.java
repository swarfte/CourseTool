package operatingSystem;

import java.util.ArrayList;;
import java.util.Comparator;

/**
 * @author swarfte
 */
public class ProcessTool {
    public static void main(String[] args) {
        ArrayList<ProcessBlock> processList = new ArrayList<>();
        processList.add(new ProcessBlock("p0", 0, 250));
        processList.add(new ProcessBlock("p1", 50, 170));
        processList.add(new ProcessBlock("p2", 130, 75));
        processList.add(new ProcessBlock("p3", 190, 100));
        processList.add(new ProcessBlock("p4", 210, 130));
        processList.add(new ProcessBlock("p5", 350, 50));

        processList.sort(Comparator.comparing(ProcessBlock::getArrivalTime));
        ProcessScheme processScheme = new ProcessScheme(processList, 100);
        processScheme.run();
        System.out.println(processScheme.showLog());
    }
}

class ProcessScheme {

    // the table of process
    private final ArrayList<ProcessBlock> processList;

    // count the running time
    private Integer currentTime = 0;

    // the speed of the process running
    private final Integer processSpeed;

    private Integer processSpeedTime = 0;

    // log the step of processing

    private final ArrayList<ProcessLog> processLogList = new ArrayList<>();

    // the queue of process
    private final ArrayList<ProcessBlock> processQueue = new ArrayList<>();

    // the current process
    private ProcessBlock currentProcess;

    // the next process in the process list or process queue
    private ProcessBlock nextProcess;

    // the queue of name, use to show the result
    private final ArrayList<String> nameQueue = new ArrayList<>();

    public ProcessScheme(ArrayList<ProcessBlock> processList, int processTime) {
        this.processList = processList;
        this.processSpeed = processTime;
        this.processSpeedTime = processTime;
    }

    private void addLog() {
        if (!emptyCurrent()) {
            processLogList.add(new ProcessLog(currentTime, currentProcess.getName(), nameQueue));
        } else {
            processLogList.add(new ProcessLog(currentTime, "", nameQueue));
        }
    }

    private void listToQueue() {
        if (!emptyList()) {
            ProcessBlock temp = processList.get(0);
            this.processQueue.add(temp);
            this.nameQueue.add(temp.getName());
            this.processList.remove(0);
        }
    }

    private void currentToQueue() {
        if (!emptyCurrent()) {
            this.processQueue.add(this.currentProcess);
            this.nameQueue.add(this.currentProcess.getName());
            dropCurrent();
        }
    }

    private void queueToCurrent() {
        if (!emptyQueue()) {
            this.currentProcess = this.processQueue.get(0);
            this.processQueue.remove(0);
            this.nameQueue.remove(0);
        }
    }

    private void getLastIn() {
        if (!emptyQueue()) {
            this.nextProcess = this.processQueue.get(this.processQueue.size() - 1);
        }
    }

    private void dropCurrent() {
        if (!emptyCurrent()) {
            this.currentProcess = null;
        }
    }

    private void dropNext() {
        if (!emptyNext()) {
            this.nextProcess = null;
        }
    }

    private boolean runProcess(int time) {
        if (!emptyCurrent()) {
            this.currentProcess.setExecutionTime(this.currentProcess.getExecutionTime() - time);
            this.processSpeedTime -= time;
            //when the process speed time finish a cycle, then reset it
            if (this.processSpeedTime == 0) {
                this.processSpeedTime = this.processSpeed;
            }
            // when finish the process, then reset the process speed time
            if (this.currentProcess.getExecutionTime() == 0) {
                this.processSpeedTime = this.processSpeed;
            }
        }
        this.currentTime += time;
        if (!emptyCurrent()) {
            // if process need to run again , then return true
            return this.currentProcess.getExecutionTime() > 0;
        } else {
            return false;
        }
    }

    private int getCurrentRunTime() {
        if (!emptyCurrent()) {
            if (this.currentProcess.getExecutionTime() > this.processSpeedTime) {
                return this.processSpeedTime;
            } else {
                return this.currentProcess.getExecutionTime();
            }
        } else {
            return 0;
        }
    }

    private boolean isArrival() {
        if (!emptyList() && !emptyCurrent()) {
            // insert the queue
            if (this.currentProcess.getExecutionTime() <= this.processSpeedTime) {
                return this.processList.get(0).getArrivalTime() <= this.currentTime + this.currentProcess.getExecutionTime();
            } else {
                return this.processList.get(0).getArrivalTime() <= this.currentTime + this.processSpeedTime;
            }
        } else if (!emptyList() && emptyCurrent()) {
            return this.processList.get(0).getArrivalTime() <= this.currentTime + this.processSpeed;

        }
        return false;
    }

    private boolean isProcessing() {
        return this.processList.size() > 0 || this.processQueue.size() > 0 || this.currentProcess != null;
    }

    private boolean emptyQueue() {
        return this.processQueue.size() == 0;
    }

    private boolean emptyList() {
        return this.processList.size() == 0;
    }

    private boolean emptyCurrent() {
        return this.currentProcess == null;
    }

    private boolean emptyNext() {
        return this.nextProcess == null;
    }

    private void addProcessInQueue() {
        listToQueue();
        getLastIn();
        runProcess(this.nextProcess.getArrivalTime() - this.currentTime);
        dropNext();
    }

    // main method for processing
    public void run() {
        while (isProcessing()) {
            // the initial state (step 1)
            if (emptyQueue() && !emptyList()) {
                // System.out.println("step 1");
                addProcessInQueue();
                addLog();
                continue;
                // after the initial state (step 2)
            }
            if (!emptyQueue() && !emptyList()) {
                //System.out.println("step 2");
                if (emptyCurrent()) {
                    queueToCurrent();
                }
                // insert the next process into the queue
                if (isArrival()) {
                    addProcessInQueue();
                    // not need to insert the next process into the queue
                } else {
                    // if not finish the process
                    if (runProcess(getCurrentRunTime())) {
                        currentToQueue();
                        queueToCurrent();
                    } else {
                        dropCurrent();
                    }
                }
                addLog();
                continue;
                // all process in the queue, so not need to insert the next process into the queue
            }
            if (!emptyQueue() && emptyList()) {
                //System.out.println("step 3");
                if (emptyCurrent()) {
                    queueToCurrent();
                }
                if (runProcess(getCurrentRunTime())) {
                    if (!emptyQueue()) {
                        currentToQueue();
                        queueToCurrent();
                    }
                } else {
                    dropCurrent();
                    if (!emptyQueue()) {
                        queueToCurrent();
                    }
                }
                addLog();
                continue;
            }
            if (emptyQueue() && emptyList() && !emptyCurrent()) {
                //System.out.println("step 4");
                if (runProcess(getCurrentRunTime())) {
                    if (!emptyQueue()) {
                        currentToQueue();
                    }
                } else {
                    dropCurrent();
                }
                addLog();
                continue;
            }
        }
    }

    public String showLog() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProcessLog processLog : processLogList) {
            stringBuilder.append(processLog.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "ProcessScheme{" +
                "processList=" + processList +
                ", currentTime=" + currentTime +
                ", processSpeed=" + processSpeed +
                ", processLogList=" + processLogList +
                '}' + "\n";
    }

}

class ProcessLog {
    private final Integer currentTime;
    private final String currentProcess;
    private final ArrayList<String> processQueue;

    public ProcessLog(Integer currentTime, String currentProcess, ArrayList<String> processQueue) {
        this.currentTime = currentTime;
        this.currentProcess = currentProcess;
        this.processQueue = (ArrayList<String>) processQueue.clone();
    }

    @Override
    public String toString() {

        return "ProcessLog{" +
                "currentTime=" + currentTime +
                ", currentProcess='" + currentProcess + '\'' +
                ", processQueue=" + processQueue.toString() +
                '}' + "\n";
    }
}

class ProcessBlock implements Comparable<ProcessBlock> {
    private String name;
    private Integer arrivalTime;
    private Integer executionTime;

    public ProcessBlock(String processName, Integer processArrivalTime, Integer processExecutionTime) {
        this.name = processName;
        this.arrivalTime = processArrivalTime;
        this.executionTime = processExecutionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public int compareTo(ProcessBlock o) {
        return Integer.compare(this.arrivalTime, o.arrivalTime);
    }

    @Override
    public String toString() {
        return "Process{" + "processName=" + name + ", processArrivalTime=" + arrivalTime + ", processExecutionTime=" + executionTime + '}' + "\n";
    }
}
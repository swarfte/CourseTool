package operating_system;

import java.util.ArrayList;

abstract class Scheduling {
    // the table of process
    protected final ArrayList<ProcessBlock> processList;

    // count the running time
    protected Integer currentTime = 0;

    // the speed of the process running
    protected final Integer processSpeed;

    // count the running time of the process in a cycle
    protected Integer processSpeedTime = 0;

    // log the step of processing
    protected final ArrayList<ProcessLog> processLogList = new ArrayList<>();

    // the queue of process
    protected final ArrayList<ProcessBlock> processQueue = new ArrayList<>();

    // the current process
    protected ProcessBlock currentProcess;

    // the next process in the process list or process queue
    protected ProcessBlock nextProcess;

    // the queue of name, use to show the result
    protected final ArrayList<String> nameQueue = new ArrayList<>();

    // no need the speed time
    protected Scheduling(ArrayList<ProcessBlock> processList) {
        this.processList = processList;
        this.processSpeed = this.processSpeedTime;
    }

    // specify the speed time
    protected Scheduling(ArrayList<ProcessBlock> processList, Integer processSpeed) {
        this.processList = processList;
        this.processSpeed = processSpeed;
    }

    // use to keep running the program
    protected boolean isProcessing() {
        return !this.processList.isEmpty() || !this.processQueue.isEmpty() || this.currentProcess != null;
    }

    // use to check the queue is empty or not
    protected boolean emptyQueue() {
        return this.processQueue.isEmpty();
    }

    // use to check the list is empty or not
    protected boolean emptyList() {
        return this.processList.isEmpty();
    }

    // use to check the current process is empty or not
    protected boolean emptyCurrent() {
        return this.currentProcess == null;
    }

    // use to check the next process is empty or not
    protected boolean emptyNext() {
        return this.nextProcess == null;
    }

    // save the processing step
    protected void addLog() {
        if (!emptyCurrent()) {
            processLogList.add(new ProcessLog(currentTime, currentProcess.getName(), nameQueue));
        } else {
            processLogList.add(new ProcessLog(currentTime, "", nameQueue));
        }
    }

    // drop the current process
    protected void dropCurrent() {
        if (!emptyCurrent()) {
            this.currentProcess = null;
        }
    }


    // drop the next process
    protected void dropNext() {
        if (!emptyNext()) {
            this.nextProcess = null;
        }
    }

    // show the log(result)
    public String showLog() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProcessLog processLog : processLogList) {
            stringBuilder.append(processLog.toString());
        }
        return stringBuilder.toString();
    }

    // determined the next process is coming or not
    protected abstract boolean isArrival();

    // put the process from list to queue
    protected abstract void listToQueue();

    // put the current process to queue
    protected abstract void queueToCurrent();

    // put the current process to queue
    protected abstract void currentToQueue();

    // run the process, then add running time
    protected abstract boolean runProcess(int time);

    // get the current run time in the current step
    protected abstract int getCurrentRunTime();

    // the main method to run the program
    protected abstract void run();
}

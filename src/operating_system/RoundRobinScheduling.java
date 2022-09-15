package operating_system;

import java.util.ArrayList;

class RoundRobinScheduling extends Scheduling {

    public RoundRobinScheduling(ArrayList<ProcessBlock> processList, int processTime) {
        super(processList,processTime);
    }


    @Override
    protected void listToQueue() {
        if (!emptyList()) {
            ProcessBlock temp = processList.get(0);
            this.processQueue.add(temp);
            this.nameQueue.add(temp.getName());
            this.processList.remove(0);
        }
    }

    @Override
    protected void currentToQueue() {
        if (!emptyCurrent()) {
            this.processQueue.add(this.currentProcess);
            this.nameQueue.add(this.currentProcess.getName());
            dropCurrent();
        }
    }

    @Override
    protected void queueToCurrent() {
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

    @Override
    protected boolean runProcess(int time) {
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

    @Override
    protected int getCurrentRunTime() {
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

    @Override
    protected boolean isArrival() {
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

    protected void addProcessInQueue() {
        listToQueue();
        getLastIn();
        runProcess(this.nextProcess.getArrivalTime() - this.currentTime);
        dropNext();
    }

    @Override
    public void run() {
        while (isProcessing()) {
            // the initial state (step 1)
            if (emptyQueue() && !emptyList()) {
                addProcessInQueue();
                addLog();
                continue;
                // after the initial state (step 2)
            }
            if (!emptyQueue() && !emptyList()) {
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
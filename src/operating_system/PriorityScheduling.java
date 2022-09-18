package operating_system;

import java.util.ArrayList;

class PriorityScheduling extends Scheduling {

    public PriorityScheduling(ArrayList<ProcessBlock> processList) {
        super(processList);
    }

    protected void swapProcess(int i , int j){
        ProcessBlock temp = this.processQueue.get(i);
        this.processQueue.set(i, this.processQueue.get(j));
        this.processQueue.set(j, temp);

        String tempName = this.nameQueue.get(i);
        this.nameQueue.set(i, this.nameQueue.get(j));
        this.nameQueue.set(j, tempName);
    }

    protected void sortQueue() {
        if (!emptyQueue()) {
            for (int i = 0; i < this.processQueue.size(); i++) {
                for (int j = i + 1; j < this.processQueue.size(); j++) {
                    if (this.processQueue.get(i).getPriority() < this.processQueue.get(j).getPriority()) {
                        swapProcess(i, j);
                    }else if (this.processQueue.get(i).getPriority().equals(this.processQueue.get(j).getPriority())) {
                        if (this.processQueue.get(i).getArrivalTime() < this.processQueue.get(j).getArrivalTime()) {
                            swapProcess(i, j);
                        }
                    }
                }
            }
        }
    }

    protected void setNext() {
        if (!emptyList()) {
            this.nextProcess = this.processList.get(0);
        }
    }

    protected void getLast() {
        if (!emptyQueue()) {
            this.nextProcess = this.processQueue.get(this.processQueue.size() - 1);
        }
    }

    @Override
    protected boolean isArrival() {
        if (!emptyList()) {
            if (!emptyCurrent()) {
                return this.processList.get(0).getArrivalTime() <= this.currentTime + this.currentProcess.getExecutionTime();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void listToQueue() {
        if (!emptyList()) {
            ProcessBlock temp = processList.get(0);
            this.processQueue.add(temp);
            this.nameQueue.add(temp.getName());
            this.processList.remove(0);
            sortQueue();
        }
    }

    @Override
    protected void queueToCurrent() {
        if (!emptyQueue()) {
            this.currentProcess = this.processQueue.get(this.processQueue.size() - 1);
            this.processQueue.remove(this.processQueue.size() - 1);
            this.nameQueue.remove(this.nameQueue.size() - 1);
        }
    }

    @Override
    protected void currentToQueue() {
        if (!emptyCurrent()) {
            this.processQueue.add(this.currentProcess);
            this.nameQueue.add(this.currentProcess.getName());
            dropCurrent();
            sortQueue();
        }
    }

    @Override
    protected boolean runProcess(int time) {
        this.currentTime += time;
        if (!emptyCurrent()) {
            this.currentProcess.setExecutionTime(this.currentProcess.getExecutionTime() - time);
            return this.currentProcess.getExecutionTime() > 0;
        } else {
            return false;
        }
    }

    @Override
    protected int getCurrentRunTime() {
        if (!emptyCurrent()) {
            return this.currentProcess.getExecutionTime();
        } else {
            return 0;
        }
    }

    protected void addProcessInQueue() {
        setNext();
        listToQueue();
        runProcess(this.nextProcess.getArrivalTime() - this.currentTime);
        dropNext();
    }

    protected boolean isSwap(ProcessBlock p1, ProcessBlock p2) {
        if (!emptyCurrent() && !emptyNext()) {
            return p1.getPriority() < p2.getPriority();
        }
        return false;
    }

    @Override
    public void run() {
        while (isProcessing()) {
            if (emptyQueue() && !emptyList()) {
                addProcessInQueue();
                if (!emptyCurrent()) {
                    getLast();
                    if (isSwap(this.nextProcess, this.currentProcess)) {
                        currentToQueue();
                        queueToCurrent();
                    }
                }
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
                    getLast();
                    if (isSwap(this.nextProcess, this.currentProcess)) {
                        currentToQueue();
                        queueToCurrent();
                    }

                    // not need to insert the next process into the queue
                } else {
                    // if not finish the process
                    if (runProcess(getCurrentRunTime())) {
                        currentToQueue();
                        queueToCurrent();
                    } else {
                        dropCurrent();
                        if (!emptyQueue()) {
                            queueToCurrent();
                        }
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

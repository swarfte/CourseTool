package os.method;

import os.process.ProcessTable;

/**
 * @author swarfte
 */
public class RoundRobinScheduler extends BasicScheduler {

    public RoundRobinScheduler(ProcessTable processTable, int processSpeed) {
        super(processTable, processSpeed);
    }

    @Override
    public int isArrival() {
        if (!emptyTable() && !emptyCurrent()) {

            // if processes can finish in one cycle
            if (this.currentProcess.getBurstTime() <= this.processCycleTime) {

                // if the next process arrives before the current process finishes
                return this.processTable.top().getArrivalTime() - (this.currentTime + this.currentProcess.getBurstTime());

                // if current process need more cycle
            } else {

                // if the next process arrives before the current cycle finishes
                return this.processTable.top().getArrivalTime() - (this.currentTime + this.processCycleTime);
            }
        } else if (!emptyTable() && emptyCurrent()) {

            // if the next process arrives before the current cycle finishes
            return this.processTable.top().getArrivalTime() - (this.currentTime + this.processSpeed);
        } else {
            return 1;
        }
    }

    @Override
    public void tableToQueue() {
        if (!emptyTable()) {
            this.processesQueue.add(this.processTable.pop());
        }
    }

    @Override
    public void queueToCurrent() {
        if (!emptyQueue()) {
            this.currentProcess = this.processesQueue.remove(0);
        }
    }

    @Override
    public void currentToQueue() {
        if (!emptyCurrent()) {
            this.processesQueue.add(this.currentProcess);
            this.dropCurrent();
        }
    }

    @Override
    public void runProcess(int time) throws IllegalStateException {
        if (!emptyCurrent()) {
            if (this.currentProcess.getBurstTime() < time) {
                throw new IllegalStateException("Process " + this.currentProcess.getName() + " burst time is less than run time");
            } else {
                this.currentProcess.setBurstTime(this.currentProcess.getBurstTime() - time);
                this.processCycleTime -= time;
            }

            // if finish current process
            if (this.currentProcess.isFinished()) {
                this.processCycleTime = this.processSpeed;
                this.dropCurrent();
            }
        }

        // if finish one cycle
        if (this.processCycleTime == 0) {
            this.processCycleTime = this.processSpeed;
            currentToQueue();
            dropCurrent();
        }
        this.currentTime += time;

    }

    @Override
    public int getCurrentProcessesRunTime() {
        if (!emptyCurrent()) {
            return Math.min(this.currentProcess.getBurstTime(), this.processCycleTime);
        } else {
            return 0;
        }
    }

    protected void test(String step) {
        System.out.println("step " + step);
    }

    protected void debug() {
        System.out.println("currentTime: " + this.currentTime);
        System.out.println("processCycleTime: " + this.processCycleTime);
        System.out.println("currentProcess: " + this.currentProcess);
        System.out.println("currentProcessBurstTime: " + this.currentProcess.getBurstTime());
        System.out.println("processesQueue: " + this.processesQueue);
        System.out.println("processTable: " + this.processTable);
    }

    @Override
    public void run() {
        while (isProcessing()) {

            // the init step
            if (emptyQueue() && !emptyTable() && emptyCurrent()) {
                // test("0 1 0");
                runProcess(this.processTable.top().getArrivalTime());
                tableToQueue();
                addRecords();
                continue;
            }

            // the second step
            if (!emptyQueue() && !emptyTable() && emptyCurrent()) {
                // test("1 1 0");
                queueToCurrent();
                addRecords();
                continue;
            }

            // the third step
            if (!emptyQueue() && !emptyTable() && !emptyCurrent()) {
                // test("1 1 1");
                int time = isArrival();
                if (time <= 0) {
                    debug();
                    System.out.println("run-time: " + (this.processCycleTime + time));
                    runProcess(this.processCycleTime + time);
                    tableToQueue();
                } else {
                    runProcess(getCurrentProcessesRunTime());
                }
                addRecords();
                continue;
            }

            if (emptyQueue() && !emptyTable() && !emptyCurrent()) {
                // test("0 1 1");
                int time = isArrival();
                if (time <= 0) {
                    runProcess(this.processCycleTime + time);
                    tableToQueue();
                } else {
                    runProcess(getCurrentProcessesRunTime());
                }
                addRecords();
                continue;
            }

            if (!emptyQueue() && emptyTable() && emptyCurrent()) {
                // test("1 0 0");
                queueToCurrent();
                addRecords();
                continue;
            }

            if (!emptyQueue() && emptyTable() && !emptyCurrent()) {
                // test("1 0 1");
                runProcess(getCurrentProcessesRunTime());
                addRecords();
                continue;
            }

            if (emptyQueue() && emptyTable() && !emptyCurrent()) {
                // test("0 0 1");
                runProcess(getCurrentProcessesRunTime());
                addRecords();
                continue;
            }

            if (emptyQueue() && emptyTable() && emptyCurrent()) {
                // test("0 0 0");
                addRecords();
                break;
            }
        }
    }
}

package os.method;


import os.process.ProcessTable;
import os.process.Processes;
import os.record.Records;
import os.record.RecordsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swarfte
 */
public abstract class BasicScheduler {
    protected final ProcessTable processTable;
    protected final RecordsTable recordsTable = new RecordsTable();
    protected List<Processes> processesQueue = new ArrayList<>();

    protected final int processSpeed;
    protected int processCycleTime;
    protected int currentTime = 0;

    protected Processes currentProcess;

    protected BasicScheduler(ProcessTable processTable) {
        this(processTable, 0);
    }

    protected BasicScheduler(ProcessTable processTable, int processSpeed) {
        this.processTable = processTable;
        this.processSpeed = processSpeed;
        this.processCycleTime = this.processSpeed;
    }

    protected boolean emptyTable() {
        return this.processTable.isEmpty();
    }

    protected boolean emptyQueue() {
        return this.processesQueue.isEmpty();
    }

    protected boolean emptyCurrent() {
        return this.currentProcess == null;
    }

    protected boolean isProcessing() {
        return !this.emptyTable() || !this.emptyQueue() || !this.emptyCurrent();
    }

    protected void dropCurrent() {
        this.currentProcess = null;
    }

    protected void addRecords() {
        if (!emptyCurrent()) {
            this.recordsTable.add(new Records(this.currentTime, this.currentProcess.getName(), this.processesQueue));
        } else {
            this.recordsTable.add(new Records(this.currentTime, "/", this.processesQueue));
        }
    }

    public void showRecords() {
        System.out.println(this.recordsTable);
    }

    @Override
    public String toString() {
        return this.processTable.toString();
    }

    protected abstract int isArrival();

    protected abstract void tableToQueue();

    protected abstract void queueToCurrent();

    protected abstract void currentToQueue();

    protected abstract void runProcess(int time);

    protected abstract int getCurrentProcessesRunTime();

    protected abstract void run();
}

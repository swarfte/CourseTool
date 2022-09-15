package operating_system;

import java.util.ArrayList;

class PriorityScheduling extends Scheduling{

    public PriorityScheduling(ArrayList<ProcessBlock> processList) {
        super(processList);
    }

    @Override
    protected boolean isArrival() {
        return false;
    }

    @Override
    protected void listToQueue() {

    }

    @Override
    protected void queueToCurrent() {

    }

    @Override
    protected void currentToQueue() {

    }

    @Override
    protected boolean runProcess(int time) {
        return false;
    }

    @Override
    protected int getCurrentRunTime() {
        return 0;
    }

    @Override
    protected void run() {

    }
}

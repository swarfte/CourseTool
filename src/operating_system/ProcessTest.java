package operating_system;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author swarfte
 */
public class ProcessTest {
    public static void main(String[] args) {
        System.out.println(new PrioritySchedulingTool());
        System.out.println(new RoundRobinSchedulingTool());
    }
}

class PrioritySchedulingTool {
    private PriorityScheduling priorityScheduling;

    // for testing
    public PrioritySchedulingTool() {
        this(new ArrayList<>() {
            {
                add(new ProcessBlock("P1", 0, 4, 1));
                add(new ProcessBlock("P2", 0, 3, 2));
                add(new ProcessBlock("P3", 6, 7, 1));
                add(new ProcessBlock("P4", 11, 4, 3));
                add(new ProcessBlock("P5", 12, 2, 2));
            }
        });
    }

    public PrioritySchedulingTool(ArrayList<ProcessBlock> prioritySchedulingProcessList) {
        prioritySchedulingProcessList.sort(Comparator.comparing(ProcessBlock::getArrivalTime));
        priorityScheduling = new PriorityScheduling(prioritySchedulingProcessList);
        priorityScheduling.run();
    }

    @Override
    public String toString() {
        return priorityScheduling.showLog();
    }
}

class RoundRobinSchedulingTool {
    RoundRobinScheduling processScheme;

    public RoundRobinSchedulingTool() {
        this(new ArrayList<>() {
            {
                add(new ProcessBlock("P0", 0, 250));
                add(new ProcessBlock("P1", 50, 170));
                add(new ProcessBlock("P2", 130, 75));
                add(new ProcessBlock("P3", 190, 100));
                add(new ProcessBlock("P4", 210, 130));
                add(new ProcessBlock("P5", 300, 50));
            }
        },100);
    }

    public RoundRobinSchedulingTool(ArrayList<ProcessBlock> roundRobinSchedulingProcessList, int processTime) {
        roundRobinSchedulingProcessList.sort(Comparator.comparing(ProcessBlock::getArrivalTime));
        processScheme = new RoundRobinScheduling(roundRobinSchedulingProcessList, processTime);
        processScheme.run();
    }

    @Override
    public String toString() {
        return processScheme.showLog();
    }
}

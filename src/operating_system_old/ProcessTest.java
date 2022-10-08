package operating_system_old;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author swarfte
 */
public class ProcessTest {
    public static void main(String[] args) {
        // for testing
//        System.out.println(new PrioritySchedulingTool());
//        System.out.println(new RoundRobinSchedulingTool());

        // for assignment 1
        ArrayList<ProcessBlock> q1 = new ArrayList<>();
        q1.add(new ProcessBlock("P1", 2, 3));
        q1.add(new ProcessBlock("P2", 4, 2));
        q1.add(new ProcessBlock("P3", 5, 1));
        q1.add(new ProcessBlock("P4", 7, 4));
        q1.add(new ProcessBlock("P5", 9, 2));
        q1.add(new ProcessBlock("P6", 15, 6));
        q1.add(new ProcessBlock("P7", 16, 8));
//        System.out.println(new RoundRobinSchedulingTool(q1, 2));

        // for assignment 2
        ArrayList<ProcessBlock> q2 = new ArrayList<>();
        q2.add(new ProcessBlock("P1", 2, 3, 3));
        q2.add(new ProcessBlock("P2", 4, 2, 2));
        q2.add(new ProcessBlock("P3", 5, 1, 1));
        q2.add(new ProcessBlock("P4", 7, 4, 2));
        q2.add(new ProcessBlock("P5", 9, 2, 1));
        q2.add(new ProcessBlock("P6", 15, 6, 8));
        q2.add(new ProcessBlock("P7", 16, 8, 2));
        System.out.println(new PrioritySchedulingTool(q2));
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

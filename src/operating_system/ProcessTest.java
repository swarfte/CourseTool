package operating_system;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author swarfte
 */
public class ProcessTest {
    public static void main(String[] args) {
        ArrayList<ProcessBlock> roundRobinSchedulingProcessList = new ArrayList<>();
        roundRobinSchedulingProcessList.add(new ProcessBlock("p0", 0, 250));
        roundRobinSchedulingProcessList.add(new ProcessBlock("p1", 50, 170));
        roundRobinSchedulingProcessList.add(new ProcessBlock("p2", 130, 75));
        roundRobinSchedulingProcessList.add(new ProcessBlock("p3", 190, 100));
        roundRobinSchedulingProcessList.add(new ProcessBlock("p4", 210, 130));
        roundRobinSchedulingProcessList.add(new ProcessBlock("p5", 350, 50));

        roundRobinSchedulingProcessList.sort(Comparator.comparing(ProcessBlock::getArrivalTime));
        RoundRobinScheduling processScheme = new RoundRobinScheduling(roundRobinSchedulingProcessList, 100);
        processScheme.run();
        System.out.println(processScheme.showLog());
    }
}

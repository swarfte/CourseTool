package os.test;

import os.method.RoundRobinScheduler;
import os.process.ProcessTable;
import os.process.Processes;
import os.record.Records;
import os.record.RecordsTable;


/**
 * @author swarfte
 */
public class RoundRobinSchedulerTest {
    public static void run() {
        practiseSamples();
        // homeworkSamples();
        // testSamples();
    }

    public static void practiseSamples() {
        ProcessTable table = new ProcessTable();
        table.add(new Processes("p0", 0, 250));
        table.add(new Processes("p1", 50, 170));
        table.add(new Processes("p2", 130, 75));
        table.add(new Processes("p3", 190, 100));
        table.add(new Processes("p4", 210, 130));
        table.add(new Processes("p5", 350, 50));
        RoundRobinScheduler scheduler = new RoundRobinScheduler(table, 100);
        scheduler.run();
        scheduler.showRecords();
    }

    public static void homeworkSamples() {

    }

    public static void testSamples() {

    }

}

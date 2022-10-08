package os.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swarfte
 */
public class ProcessTable {
    private final List<Processes> processes;

    public ProcessTable() {
        this.processes = new ArrayList<>() ;
    }

    public ProcessTable(List<Processes> processes) {
        this.processes = processes;
    }

    public void add(Processes process) {
        this.processes.add(process);
    }

    public Processes pop() {
        return this.processes.remove(0);
    }

    public Processes top() {
        return this.processes.get(0);
    }

    public boolean isEmpty() {
        return this.processes.isEmpty();
    }

    @Override
    public String toString() {
        return this.processes.toString();
    }
}

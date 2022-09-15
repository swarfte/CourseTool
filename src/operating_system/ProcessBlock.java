package operating_system;

class ProcessBlock implements Comparable<ProcessBlock> {
    protected String name;
    protected Integer arrivalTime;
    protected Integer executionTime;
    protected Integer priority;

    public ProcessBlock(String processName, Integer processArrivalTime, Integer processExecutionTime) {
        this.name = processName;
        this.arrivalTime = processArrivalTime;
        this.executionTime = processExecutionTime;
    }

    public ProcessBlock(String processName, Integer processArrivalTime, Integer processExecutionTime, Integer priority) {
        this.name = processName;
        this.arrivalTime = processArrivalTime;
        this.executionTime = processExecutionTime;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public int compareTo(ProcessBlock o) {
        return Integer.compare(this.arrivalTime, o.arrivalTime);
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ProcessBlock{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", executionTime=" + executionTime +
                ", priority=" + priority +
                '}';
    }
}

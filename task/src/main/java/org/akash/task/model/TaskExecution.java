package org.akash.task.model;

import java.time.Instant;

public class TaskExecution {
    private Instant startTime;
    private Instant endTime;
    private String output;

    public TaskExecution() {}

    public TaskExecution(String output, Instant startTime, Instant endTime) {
        this.output = output;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}

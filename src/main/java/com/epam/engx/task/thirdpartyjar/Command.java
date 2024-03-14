package com.epam.engx.task.thirdpartyjar;

public interface Command {
    boolean canProcess(String command);
    void process(String command);


}
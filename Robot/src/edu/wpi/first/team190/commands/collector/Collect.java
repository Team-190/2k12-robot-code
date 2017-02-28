package edu.wpi.first.team190.commands.collector;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Mitchell
 */
public class Collect extends CommandBase{
    /**
     * collect until canceled
     */
    public Collect(){
        this(-1);
    }
    private int numToCollect;
    public Collect(int numToCollect){
        this.numToCollect = numToCollect;
    }
    protected void initialize() {
        collector.setCollect(numToCollect);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return !collector.isCollecting();
    }

    protected void end() {
        collector.stopCollecting();
    }

    protected void interrupted() {
        collector.stopCollecting();
    }
}

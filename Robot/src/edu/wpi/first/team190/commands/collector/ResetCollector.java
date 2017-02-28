package edu.wpi.first.team190.commands.collector;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Mitchell
 */
public class ResetCollector extends CommandBase{
     private boolean done = false;
    public ResetCollector(){
        requires(collector);
    }
    protected void initialize() {
        collector.reset();
        done = true;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
        collector.stopCollecting();
    }

    protected void interrupted() {
        collector.stopCollecting();
    }
}

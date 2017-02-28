/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.collector;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Mitchell
 */
    
public class DefaultCollectorCommand extends CommandBase{
    private boolean up;
    public DefaultCollectorCommand(boolean up){
        requires(collector);
        this.up = up;
    }
    protected void initialize() {
        if(!up)
            collector.reset();
    }

    protected void execute() {
        if(up)
            collector.defaultRunUp();
        else
            collector.defaultRunDown();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stop();
    }

    protected void interrupted() {
        end();
    }
}


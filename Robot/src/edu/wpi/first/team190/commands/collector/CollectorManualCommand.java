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
public class CollectorManualCommand extends CommandBase{
    private double speed;
    public CollectorManualCommand(double speed){
        requires(collector);
        this.speed = speed;
    }
    
    protected void initialize() {
        collector.setSpeed(speed);
    }

    protected void execute() {
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

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
public class CollectorSensorMonitorCommand extends CommandBase{
    public CollectorSensorMonitorCommand(){
        setInterruptible(false);
        setRunWhenDisabled(true);
    }
    protected void initialize() {
        System.out.println("Collector Sensor Monitor Started");
    }

    protected void execute() {
        collector.processSensor();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        System.err.println("Collector monitor thread ended");
    }

    protected void interrupted() {
        System.err.println("Collector monitor thread interrupted");
    }
    
}

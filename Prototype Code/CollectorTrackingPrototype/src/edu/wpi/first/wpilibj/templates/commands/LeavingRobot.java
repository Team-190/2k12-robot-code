/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;


/**
 *
 * @author Fred
 */
public class LeavingRobot extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        ballCount.removeBall();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Brendan
 */
public class StopShooter extends  CommandBase{

    public StopShooter() {
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.resetPID();
        shooter.setSetpoint(0.0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}

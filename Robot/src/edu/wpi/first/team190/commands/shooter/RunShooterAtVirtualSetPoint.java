/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Greg
 */
public class RunShooterAtVirtualSetPoint extends CommandBase{

    public RunShooterAtVirtualSetPoint(){
        setTimeout(12.5);
        requires(shooter);
    }

    protected void initialize() {
        shooter.runShooterAtVirtualSetpoint();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        boolean result = shooter.atDesiredSpeed();
        System.out.println("At Speed: " + result);
        return (result || isTimedOut());
    }

    protected void end() {
        if (isTimedOut())
        {
            shooter.usePIDValue=false;
        }
    }

    protected void interrupted() {
    }

}

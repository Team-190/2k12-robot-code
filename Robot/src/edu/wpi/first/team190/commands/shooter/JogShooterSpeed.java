/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author alex
 */
public class JogShooterSpeed extends CommandBase {
    double speed;
    public JogShooterSpeed(double speed) {
        requires(shooter);
        this.speed = speed;
    }
  protected void initialize() {
        shooter.setSetpointRelative(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shooter.atDesiredSpeed();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

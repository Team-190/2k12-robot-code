/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Demo
 */
public class ShooterRunByStick extends CommandBase {
    public ShooterRunByStick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.runMotor(oi.getVelocity());
        SmartDashboard.putDouble("Out", oi.getVelocity());
        SmartDashboard.putDouble("RPM", shooter.getRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Alex
 */
public class ShooterRunAtSpeed extends CommandBase {
    public ShooterRunAtSpeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        //this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        shooter.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try{
        shooter.setSetpoint(SmartDashboard.getDouble("Desired RPM"));
        }catch(NetworkTableKeyNotDefined e){
            e.printStackTrace();
        }
        SmartDashboard.putDouble("RPM", shooter.getRPM());
        //System.out.println(shooter.getRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        shooter.disable();
    }
}

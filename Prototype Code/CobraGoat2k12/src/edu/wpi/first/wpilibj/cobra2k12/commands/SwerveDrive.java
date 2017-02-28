/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Mitchell
 */
public class SwerveDrive extends CommandBase{
    private double lastDirection = 0;
    public SwerveDrive(){
        requires(driveTrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        if (oi.getMagnitude() > 0.075) {
            driveTrain.driveSwerve(
                    oi.getDirectionRadians()+Math.PI,
                    oi.getMagnitude());
            lastDirection = oi.getDirectionRadians()+Math.PI;
            SmartDashboard.putDouble("Joystick Value", oi.getDirectionRadians());
        } else {
            driveTrain.driveSwerve(
                    lastDirection,
                    0.0);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
            driveTrain.driveSwerve(lastDirection, 0, 0);
    }

    protected void interrupted() {
        end();
    }
    
}

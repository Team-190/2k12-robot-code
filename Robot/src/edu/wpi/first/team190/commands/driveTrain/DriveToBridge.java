/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.driveTrain;

import edu.wpi.first.team190.commands.CommandBase;

/**
 * Robot will drive up to the bridge.
 *
 * @author Dominik Smreczak
 */
public class DriveToBridge extends CommandBase {
    // TODO: WAIT: Use Encoders
    public DriveToBridge(double timeToDie) {
        requires(drivetrain);
        setTimeout(timeToDie);
        
    }
    
    protected void initialize() {
        
    }

    
    protected void execute() {
        drivetrain.tankDrive(-0.70,-0.70);
    }

    
    protected boolean isFinished() {
        return (bridgeTipper.atBridge() || isTimedOut());
    }

    
    protected void end() {
        drivetrain.tankDrive(0, 0);
    }

    
    protected void interrupted() {
        drivetrain.tankDrive(0,0);
    }
}

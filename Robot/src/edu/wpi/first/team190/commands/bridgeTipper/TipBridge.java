/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.bridgeTipper;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Tips the bridge using current control.
 *
 * @author alex
 */
public class TipBridge extends CommandBase {
    
    public TipBridge() {
        requires(bridgeTipper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        bridgeTipper.disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        SmartDashboard.putDouble("Current", bridgeTipper.getAmps());
        bridgeTipper.pushBridgeDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        bridgeTipper.enable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        bridgeTipper.enable();
    }
}

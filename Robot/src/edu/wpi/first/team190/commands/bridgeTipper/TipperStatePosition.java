/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.bridgeTipper;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author mwills
 */
public class TipperStatePosition extends CommandBase{

    public TipperStatePosition(){
        requires(bridgeTipper);
    }

    protected void initialize() {
        bridgeTipper.enable();
    }

    protected void execute() {
        SmartDashboard.putDouble("Current", bridgeTipper.getAmps());
        bridgeTipper.setStatePosition();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.bridgeTipper;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Greg
 */
public class DisableBridgeTipper extends CommandBase{
    public DisableBridgeTipper(){
        setInterruptible(false);
        setRunWhenDisabled(true);
        requires(bridgeTipper);
    }

    protected void initialize() {
    }

    protected void execute() {
        bridgeTipper.disable();
        bridgeTipper.motor.set(0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

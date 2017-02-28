/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.bridgeTipper;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author mwills
 */
public class ChangeTipperState extends CommandBase{

    private boolean up;
    private boolean done = false;
    public ChangeTipperState(boolean up){
        this.up = up;
    }

    protected void initialize() {
        if(up)
            bridgeTipper.upState();
        else
            bridgeTipper.downState();
        done = true;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

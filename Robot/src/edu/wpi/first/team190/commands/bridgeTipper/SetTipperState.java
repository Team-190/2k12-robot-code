/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.bridgeTipper;

import edu.wpi.first.team190.commands.CommandBase;

/**
 * Moves bridge tippers down to detect bridge.
 *
 * @author Dominik Smreczak
 */
public class SetTipperState extends CommandBase {
    private int state;
    private boolean done = false;
    
    public SetTipperState(int state) {
        this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        bridgeTipper.setState(state);
        done = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

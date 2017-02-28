/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Greg
 */
public class JKCountIncrease extends CommandBase{

    protected void initialize() {
        shooter.increaseJKCount();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

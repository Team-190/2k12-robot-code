/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Waits for some period of time as set on the SmartDashboard.
 * 
 * @author Aaron
 */
public class SmartDashboardWait extends CommandBase {
    public void initialize(){
        setTimeout(Preferences.getInstance().getDouble("Autonomous Delay", 0.0));
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {}
    protected void interrupted() {}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Greg
 */
public class HoldShooterTemp extends CommandBase{

    public HoldShooterTemp() {
        setTimeout(5.0);
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        shooter.setSetpoint(0.0);
    }

    protected void interrupted() {
    }


}

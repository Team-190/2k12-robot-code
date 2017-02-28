/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.driveTrain;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg
 */
public class DriveSwerveViaJoysticks extends CommandBase{

    public DriveSwerveViaJoysticks() {
        requires(drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        drivetrain.swerveDrive((oi.getRightStickAngle() + Math.PI) % (2*Math.PI), oi.getLeftStickMagnitude());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

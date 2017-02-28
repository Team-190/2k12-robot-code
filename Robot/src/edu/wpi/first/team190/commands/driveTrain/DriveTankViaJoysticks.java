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
public class DriveTankViaJoysticks extends CommandBase{

    public DriveTankViaJoysticks() {
        requires(drivetrain);    
    }

    protected void initialize() {
    }

    protected void execute() {
//        System.out.println("LY: " + oi.getLeftStickY() +
//                ", LX: " + oi.getLeftStickX() +
//                ", RY: " + oi.getRightStickY() +
//                ", RX: " + oi.getRightStickX() +
//                ", Rtheta: " + oi.getRightStickAngle() +
//                ", Throttle: " + oi.driverGamePad.getThrottle() +
//                ", Buttons: " + oi.driverGamePad.getRawButton(1)+", "
//                + oi.driverGamePad.getRawButton(2)+", "
//                + oi.driverGamePad.getRawButton(3)+", "
//                + oi.driverGamePad.getRawButton(4)+", "
//                + oi.driverGamePad.getRawButton(5)+", "
//                + oi.driverGamePad.getRawButton(6)+", "
//                + oi.driverGamePad.getRawButton(7)+", "
//                + oi.driverGamePad.getRawButton(8)+", "
//                + oi.driverGamePad.getRawButton(9)+", "
//                + oi.driverGamePad.getRawButton(10)+", "
//                + oi.driverGamePad.getRawButton(11)+", "
//                );
        drivetrain.tankDrive(-oi.getLeftStickY(), -oi.getRightStickY());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

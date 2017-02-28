/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.buttons;

import edu.wpi.first.team190.Team190Robot;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Brendan
 */
public class DriveTrigger extends Button {
    
    public DriveTrigger() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    public boolean get() {
        return (CommandBase.drivetrain.isDrivingForward() && !Team190Robot.isInAuto());
    }

}

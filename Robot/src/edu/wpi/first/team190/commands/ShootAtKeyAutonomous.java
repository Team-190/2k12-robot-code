
package edu.wpi.first.team190.commands;

import edu.wpi.first.team190.commands.shooter.MoveBallToShooter;
import edu.wpi.first.team190.commands.shooter.RunShooterAtVirtualSetPoint;
import edu.wpi.first.team190.commands.turret.TurnToTargetLowPassFilter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Aaron
 */

//Assumes that the robot is placed at the key as is required at the beginning of the match
public class ShootAtKeyAutonomous extends CommandGroup {
    
    public ShootAtKeyAutonomous() {
        addSequential(new WaitCommand(2.0));
        addSequential(new TurnToTargetLowPassFilter(), 4.0);
        addSequential(new RunShooterAtVirtualSetPoint());
        addSequential(new MoveBallToShooter(true));
//        addSequential(new RunShooterFromCamera(null));
//        //addSequential(new SmartDashboardWait());
//        addSequential(new MoveBallToShooter(true)); // TODO: Take input from smartdashboard (see Alex)
        //addSequential(new Collect());
        //addSequential(new AutoFire());
    }
}

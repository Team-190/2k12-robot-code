/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Greg
 */
public class FireSequence extends CommandGroup{

    public FireSequence() {
        addSequential(new RunShooterAtVirtualSetPoint());
        addSequential(new MoveBallToShooter());
    }

    protected void interrupted() {
        super.interrupted();
        CommandBase.shooter.cancelShoot();
    }

    protected void end() {
        super.end();
        CommandBase.shooter.cancelShoot();
    }
}

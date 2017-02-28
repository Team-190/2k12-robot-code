/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands;

import edu.wpi.first.team190.commands.bridgeTipper.SetTipperState;
import edu.wpi.first.team190.commands.collector.Collect;
import edu.wpi.first.team190.commands.collector.ContinuousCollect;
import edu.wpi.first.team190.commands.driveTrain.DriveToBridge;
import edu.wpi.first.team190.commands.shooter.FireSequence;
import edu.wpi.first.team190.commands.shooter.MoveBallToShooter;
import edu.wpi.first.team190.commands.shooter.SetVirtualSetpoint;
import edu.wpi.first.team190.commands.turret.TurnToTargetLowPassFilterHybrid;
import edu.wpi.first.team190.subsystems.BridgeTipper;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Paul Harrington
 */
public class CoopBridgeAutonomous extends CommandGroup {
    
    public CoopBridgeAutonomous() {
        //SmartDashboard.putDouble("Camera Time", 5.0);
        addSequential(new SetTipperState(BridgeTipper.READY_STATE));
        addParallel(new SetVirtualSetpoint(SetVirtualSetpoint.HYBRID_LOCATION));
        addSequential(new DriveToBridge(2.0));
        addParallel(new ContinuousCollect());
        addSequential(new SetTipperState(BridgeTipper.DOWN_STATE));
        
       // addParallel(new WaitThenShoot());

        addSequential(new TurnToTargetLowPassFilterHybrid(4.0));          
        addSequential(new FireSequence());
        addSequential(new FireSequence());
        addSequential(new FireSequence());
        addSequential(new FireSequence());
        addSequential(new MoveBallToShooter(true)); // TODO: Take input from smartdashboard (see Alex)
    }
    
    private class WaitThenShoot extends CommandGroup{

        public WaitThenShoot() {
            this.addSequential(new WaitCommand(1.5));
            this.addSequential(new FireSequence());
        }
        
    }
}
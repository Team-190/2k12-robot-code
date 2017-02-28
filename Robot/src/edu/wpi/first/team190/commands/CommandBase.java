package edu.wpi.first.team190.commands;

import edu.wpi.first.team190.OI;
import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    
    // Create a single static instance of all of your subsystems
    public static DriveTrain drivetrain = new DriveTrain();
    public static Shooter shooter = new Shooter();
    public static Collector collector = new Collector();//Must be initialized after shooter
    public static Turret turret = new Turret();
    public static BridgeTipper bridgeTipper = new BridgeTipper();
    public static IndicatorLights lights = new IndicatorLights();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = OI.getInstance();

        // Show what command your subsystem is running on the SmartDashboard
        //SmartDashboard.putData(collector);
        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(turret);
        SmartDashboard.putData(bridgeTipper);
        //SmartDashboard.putData(lights);
        SmartDashboard.putData(Camera.getInstance());
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}

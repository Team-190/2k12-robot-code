/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.team190;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.robottesting.commands.TestBatteryVoltage;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team190Robot extends IterativeRobot {

    Command autonomousCommand;
    TestBatteryVoltage tbvCommand;
    private static boolean isAuto=false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Write automated test failures to console
//        ProblemManager.getInstance().addHandler(new ProblemHandler() {
//
//            public void handleProblem(AssertionFailure af) {
//                String prefix = "";
//                if(af.getLevel() == AssertionFailure.Level.ERROR)
//                    prefix = "ERROR";
//                else if (af.getLevel() == AssertionFailure.Level.WARNING)
//                    prefix = "WARNING";
//
//                System.err.println("[SELFTEST " + prefix + "] " + af.getName() + ": " + af.getFailureDescription());
//            }
//        });

        // Start monitoring battery voltage (to run continuously)
//        tbvCommand = new TestBatteryVoltage();
//        tbvCommand.start();

        // Initialize all subsystems && OI must be called first
        CommandBase.init();
        Logger.init();
/*
        new CommandBase(){{
            setRunWhenDisabled(true);
        }

            protected void initialize() {
            }

            protected void execute() {
                //SmartDashboard.putBoolean("Bridge Limit", bridgeTipper.atBridge());
                //SmartDashboard.putDouble("Bridge Angle", bridgeTipper.getPosition());
                //SmartDashboard.putDouble("Swerve Angle", drivetrain.getSwerveAngle());
                //SmartDashboard.putDouble("Left Drive Encoder", drivetrain.getLeftEncoder());
                //SmartDashboard.putDouble("Right Drive Encoder", drivetrain.getRightEncoder());
                //SmartDashboard.putDouble("Turret Pot", turret.getCurrentAngle());
                //SmartDashboard.putDouble("Turret Pot Voltage", turret.getAverageVoltage());
                SmartDashboard.putDouble("RPM", shooter.getRPM());
            }

            protected boolean isFinished() {
                return false;
            }

            protected void end() {
            }

            protected void interrupted() {
            }

        }.start();*/
       
        //Camera.init();

    }

    public void autonomousInit() {
        isAuto = true;
        CommandBase.shooter.zeroRPMOffsets();
        CommandBase.turret.zeroAngleOffsets();
        CommandBase.shooter.usePIDValue=true;
        // instantiate the command used for the autonomous period
        autonomousCommand = (Command) (OI.getInstance().auton.getSelected());
        // schedule the autonomous command (example)
        autonomousCommand.start();
        Logger.write("Entering Auto!\r\n\r\n");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        Logger.write("Entering TeleOp!\r\n\r\n");
        isAuto = false;
        CommandBase.shooter.zeroRPMOffsets();
        CommandBase.shooter.usePIDValue=true;
        CommandBase.turret.zeroAngleOffsets();
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if(autonomousCommand != null){
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
  
    public static boolean isInAuto(){
        return isAuto;
    }
}

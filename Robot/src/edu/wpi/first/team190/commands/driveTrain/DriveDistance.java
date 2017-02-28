/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.driveTrain;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Mitchell
 */
public class DriveDistance extends CommandBase{
    public static final double SPEED_CONST = 1;
    public static final double ANGLE_CONST = 1;
    private double distance;
    public DriveDistance(double distance){
        requires(drivetrain);
        this.distance = distance;
    }
    private double startLeft;
    private double startRight;
    protected void initialize() {
        startLeft = drivetrain.getLeftEncoder();
        startRight = drivetrain.getRightEncoder();
    }
    
    private double leftDist(){
        return drivetrain.getLeftEncoder()-startLeft;
    }
    private double rightDist(){
        return drivetrain.getRightEncoder()-startRight;
    }

    protected void execute() {
        drivetrain.getDrive().arcadeDrive(SPEED_CONST*(leftDist()-distance), ANGLE_CONST*(leftDist()-rightDist()));
    }

    protected boolean isFinished() {
        return leftDist()>=distance && rightDist()>=distance;
    }
    

    protected void end() {
    }

    protected void interrupted() {
    }
    
}

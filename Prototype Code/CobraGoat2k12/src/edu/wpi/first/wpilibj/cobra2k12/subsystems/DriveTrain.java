/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.cobra2k12.RobotMap;
import edu.wpi.first.wpilibj.cobra2k12.commands.TankDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg Granito
 */
public class DriveTrain extends PIDSubsystem {

    Jaguar leftDrive1 = new Jaguar(RobotMap.leftDrive);
  //  Jaguar leftDrive2 = new Jaguar(RobotMap.LEFT_DRIVE_MOTOR_2);

    Jaguar rightDrive1 = new Jaguar(RobotMap.rightDrive);
   // Jaguar rightDrive2 = new Jaguar(RobotMap.RIGHT_DRIVE_MOTOR_2);

    Jaguar swerveMotor1 = new Jaguar(RobotMap.frontTurnMotor);
    Jaguar swerveMotor2 = new Jaguar(RobotMap.rearTurnMotor);

    AbsoluteAngleSensor swerveSensor = new IndexedEncoder(7, 6, 5, true);

    boolean inverted = false;

    public DriveTrain() {
        super(1, 0, 0); //Unofficial values
        SmartDashboard.putDouble("ANGLE OFFSET", 0.0);
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setInputRange(0, 2*Math.PI);
        getPIDController().setContinuous(true);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

    public void initializeSwerve() {
        getPIDController().disable();
        swerveMotor1.set(.75);
        swerveMotor2.set(.75);
    }

    public void finishInitialize() {
        swerveMotor1.set(0);
        swerveMotor2.set(0);
        getPIDController().enable();
    }

    public void driveTank(double left, double right){
        driveSwerve(0.0, right, left);
    }

    public void driveSwerve(double angle, double power){
        driveSwerve(angle, power, power);
    }

    public void driveSwerve(double angle, double right, double left){
        double desAngle;
        double desLeftPwr = left;
        double desRightPwr = right;
        double curAngle = swerveSensor.getAngle();
        try{
            desAngle = (angle + SmartDashboard.getDouble("ANGLE OFFSET")) %(2.0*Math.PI);
        }catch(Exception e){
            desAngle = angle;
        }

        if(inverted){
            desAngle = (desAngle + Math.PI) % (2.0 * Math.PI);
            desLeftPwr = -desLeftPwr;
            desRightPwr = -desRightPwr;
        }

        double deltaAngle  = Math.abs(curAngle-desAngle);

        if(deltaAngle < (1.5 * Math.PI) && deltaAngle > (0.5 * Math.PI)){
            inverted = !inverted;
            desAngle = (desAngle + Math.PI) % (2.0 * Math.PI);
            desLeftPwr = -desLeftPwr;
            desRightPwr = -desRightPwr;
        }

        setSetpoint(desAngle);
        leftDrive1.set(desLeftPwr);

        rightDrive1.set(desRightPwr);
        
    }

    protected double returnPIDInput() {
        return swerveSensor.getAngle();
    }

    protected void usePIDOutput(double output) {
        swerveMotor1.set(output);
        swerveMotor2.set(output);
    }

}

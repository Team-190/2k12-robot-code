/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.commands.driveTrain.DriveTankViaJoysticks;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.robottesting.commands.TestChangePositionContinuous;
import edu.wpi.first.wpilibj.robottesting.commands.TestableSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg Granito
 */
public class DriveTrain extends PIDSubsystem implements TestableSubsystem {

    private Jaguar leftDrive = new Jaguar(RobotMap.LEFT_DRIVE_MOTOR);
    private Jaguar rightDrive = new Jaguar(RobotMap.RIGHT_DRIVE_MOTOR);
    public Jaguar swerveMotor = new Jaguar(RobotMap.SWERVE_MOTOR);//TODO make private
    private AnalogChannel swervePot = new AnalogChannel(RobotMap.SWERVE_SENSOR);
    public Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B);
    private Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B);
    private boolean inverted = false;
    private double resultantDrive = 0.0;
    private double theta = 0.0;
    private RobotDrive drive;
    
    /**
     * PID Values for the swerve drive
     */
    private static double Kp_S = -10; //TODO: Test value
    private static double Ki_S = 0; //TODO: Test value
    private static double Kd_S = 0; //TODO: Test value
    private static double SWERVE_CENTER = Math.PI+.125;//3.106
    public DriveTrain() {
        super(Kp_S, Ki_S, Kd_S); // TODO: TEST: Unofficial values
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setTolerance(2.0);
        getPIDController().setInputRange(0, 2 * Math.PI);
        getPIDController().setContinuous(true);
        
        leftEncoder.start();
        rightEncoder.start();

        // ((36/18) * (14/56))^(-1) * 6pi
        leftEncoder.setDistancePerPulse(12*Math.PI/360); // TODO: Test: Correct value?
        rightEncoder.setDistancePerPulse(12*Math.PI/360); // TODO: Test: Correct value?
        
        drive = new RobotDrive(leftDrive, rightDrive);
        drive.setSafetyEnabled(false);
        
        SmartDashboard.putDouble(RobotMap.DRIVETRAIN_ANGLE_OFFSET, SWERVE_CENTER);

        enable();
    }
    
    public RobotDrive getDrive(){
        return drive;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveTankViaJoysticks());
    }
   //Drive the motor using tank drive by giving the swerve command an angle of 0
    public void tankDrive(double left, double right) {
        swerveDrive(0.0, right, left);
    }

    //If given one power, use it for both motors
    public void swerveDrive(double angle, double power) {
        swerveDrive(angle, power, power);
    }

    public void swerveDrive(double angle, double right, double left) {
        theta = angle;
        resultantDrive = (left + right)/2;
        double desAngle;
        double desLeftPwr = left;
        double desRightPwr = right;
        
        double curAngle = getSwerveAngle();
        try{
            desAngle = (angle + SmartDashboard.getDouble(RobotMap.DRIVETRAIN_ANGLE_OFFSET)) %(2.0*Math.PI);
        }catch(Exception e){
            desAngle = (angle + SWERVE_CENTER) %(2.0*Math.PI);
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
        
        drive.tankDrive(desLeftPwr, desRightPwr);
        
    }
    

    /*
     * Radians
     */
    public double getSwerveAngle() {
        double rawAngle = (Math.min(2 * Math.PI, Math.max((swervePot.getAverageVoltage() * ((2 * Math.PI) / 5.124)/*Radians/Volts*/), 0)));
        try {
             return (rawAngle - SmartDashboard.getDouble(RobotMap.DRIVETRAIN_ANGLE_OFFSET) + 2*Math.PI) % (2.0 * Math.PI);
            // TODO: TEST: Find the correct offset and hardcode the offset
        } catch (Exception e) {
            return rawAngle;
        }
    }

    protected double returnPIDInput() {
        return getSwerveAngle();
    }

    protected void usePIDOutput(double output) {
        swerveMotor.set(output);
    }
    
    public double getLeftEncoder(){
        return leftEncoder.getDistance();
    }
    public double getRightEncoder(){
        return rightEncoder.getDistance();
    }

    /**
     * @see TestableSubsystem#getTestCommands().
     */
    public CommandGroup getTestCommands() {
        CommandGroup driveTrainTests = new CommandGroup("DriveTrainSelfTest");

        driveTrainTests.addSequential(new TestChangePositionContinuous("RotateDriveModulesCounterClockwise",    // Name
                                                                       this,                                    // Subsystem
                                                                       swerveMotor,                             // Motor to use
                                                                       swervePot,                               // Sensor to read from
                                                                       0.3,                                     // Power to apply
                                                                       0.1,                                     // Expected change in position pot (sign is important)
                                                                       0.5));                                   // Time for which to apply power before checking
        driveTrainTests.addSequential(new WaitCommand(1.0)); // Allow motor to stop

        driveTrainTests.addSequential(new TestChangePositionContinuous("RotateDriveModulesClockwise",    // Name
                                                                       this,                                    // Subsystem
                                                                       swerveMotor,                             // Motor to use
                                                                       swervePot,                               // Sensor to read from
                                                                       -0.3,                                     // Power to apply
                                                                       -0.1,                                     // Expected change in position pot (sign is important)
                                                                       0.5));                                   // Time for which to apply power before checking
        driveTrainTests.addSequential(new WaitCommand(1.0)); // Allow motor to stop

        return driveTrainTests;
    }
    
    public boolean isDrivingForward()
    {
        double modTheta = ((theta % (2*Math.PI)) + (2*Math.PI)) % (2*Math.PI);
        return((modTheta<(Math.PI/4) || modTheta > (7*Math.PI/4)) && resultantDrive < -0.1)||
               ((modTheta<(5*Math.PI/4) && modTheta > (3*Math.PI/4)) && resultantDrive > 0.1);
    }
}

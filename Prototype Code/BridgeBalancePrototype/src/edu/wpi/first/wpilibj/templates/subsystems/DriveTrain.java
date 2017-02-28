package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.ADXL345_SPI;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.SPIDevice;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * <p>The drive train is PID subsystem, but unlike the {@link Wrist} and
 * {@link Elevator}, it is not always running PID. Instead, it can be run in a
 * manual tank drive or PID can be enabled and it will use a range finder to
 * drive a fixed distance away from the target.</p>
 * 
 * <p>Recommended next step: {@link CommandBase}</p>
 *
 * @author Alex Henning
 */
public class DriveTrain extends PIDSubsystem {

    // The PID constants for angle control
    private static final double Kp = 0.1;
    private static final double Ki = 0;
    private static final double Kd = 0.0;
    
    RobotDrive drive;
    ADXL345_SPI accel;

    // Initialize your subsystem here
    public DriveTrain() {
        super("DriveTrain", Kp, Ki, Kd);
        
        Watchdog.getInstance().kill();
        drive = new RobotDrive(RobotMap.leftMotor, RobotMap.rightMotor);
        
        SPIDevice.initBus(5, 6, 7);
        accel = new ADXL345_SPI(new DigitalOutput(8), ADXL345_SPI.DataFormat_Range.k2G);
        //getPIDController().setInputRange(-90, 90);
        getPIDController().setOutputRange(-.5, .5);
        //getPIDController().setTolerance(15.0);
    }

    /**
     * Set the default command to drive with joysticks.
     */
    public void initDefaultCommand() {}

    /**
     * @return The value of the rangefinder used as the PID input device.
     *         These values correspond to your PID setpoint, in this case the
     *         value can be anywhere between 0v and 5v.
     */
    protected double returnPIDInput() {
        SmartDashboard.putDouble("Angle", getAngle());
        return getAngle();
    }
    
    public double getAngle() {
        return Math.toDegrees(MathUtils.atan(accel.getAcceleration(ADXL345_SPI.Axes.kY) / accel.getAcceleration(ADXL345_SPI.Axes.kZ)));
    }

    /**
     * @param output The value to set the output to as determined by the PID
     *               algorithm. This gets called each time through the PID loop
     *               to update the output to the motor.
     */
    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Output:", output);
        drive.tankDrive(output, output);
    }
    
    /**
     * Implements the tank drive capability of the drivetrain.
     * 
     * @param left The speed for the left side of the drivetrain.
     * @param right The speed for the right side of the drivetrain.
     */
    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }

    public boolean isBalanced() {
        return (Math.abs(getAngle()) <= 5);
    }
}

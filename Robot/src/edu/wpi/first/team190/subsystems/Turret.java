package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.OI;
import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.camera.NoTargetException;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg Granito
 * @author Fredric Silberberg
 */
public class Turret extends PIDSubsystem {
    
    public Victor motor = new Victor(RobotMap.TURRET_MOTOR);//TODO make private
    private AnalogChannel pot = new AnalogChannel(RobotMap.TURRET_SENSOR);
    private Gyro gyro = null;

    private double angleTrimZero = 0.0;

    //degrees
    public static final double LEFT_BOUND = -80.0;
    public static final double RIGHT_BOUND = 80.0;
    private static final double THRESHOLD = 0.2;
    
    public Turret() {
        super(0.15, 0.015, 0.0); // TODO: TEST: Unofficial values 
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setInputRange(LEFT_BOUND, RIGHT_BOUND);
        getPIDController().setContinuous(false);
        gyro = new Gyro(RobotMap.TURRET_GYRO);
        gyro.setSensitivity(.007); // TODO: TEST: Value should be about right, possibly needs to be tuned
        resetGyro();
        SmartDashboard.putDouble("CFSOffset", 1);
        SmartDashboard.putDouble("CameraP", -.15);
        //SmartDashboard.putDouble("AllainceOffset", 0.0);
    }

    public void initDefaultCommand() {}

    
    /**
     * This will take an angle and attempt to set the setpoint to it.
     * If the angle is in our 90 degree deadzone, it will get as close as possible
     * 
     * @param angle The value which we are trying to move to.
     */
    public void setSetpoint(double angle) {
        double offset = (OI.getInstance().getRawAngleTrim()-angleTrimZero)*3.125;
        //double allainceOffset = SmartDashboard.getDouble("AllainceOffset", 0.0);
        angle-=offset; //+ allainceOffset;
        setRawSetpoint(angle);
    }

    private void setRawSetpoint(double angle){
        if(angle > RIGHT_BOUND){
            angle = RIGHT_BOUND;
        }else if(angle < LEFT_BOUND){
            angle = LEFT_BOUND;
        }
        super.setSetpoint(angle);
    }

    /**
     * Returns the onTarget function of the PIDSubsystem.
     * 
     * @return Returns whether the PID Controller is at it's setpoint
     */
    public boolean onTarget() {
        return Math.abs(getCurrentAngle() - getSetpoint()) < THRESHOLD;
    }

    /**
     * Get the current angle of the turret according the pot.
     * 
     * @return  Returns the current angle of the turret through the potentiometer
     */
    public double getCurrentAngle() {
        return convertVoltageToDeg(pot.getAverageVoltage());
    }
    public double getAverageVoltage() {
        return pot.getAverageVoltage();
    }

    protected double returnPIDInput() {
        double position = getCurrentAngle();
        SmartDashboard.putDouble("Turret Angle", position);
        return position;
    }

    private boolean pidControl = false;
    protected void usePIDOutput(double output) {
        double angle = getCurrentAngle();
        double delta = getSetpoint() - angle;
        
        double actOutput=0;
 
        if(Math.abs(delta)<.2){
            motor.set(0.0);
            return;
        }


        if(delta > 5.0){
            pidControl = false;
            actOutput = -1.0;
        }else if(delta < -5.0){
            pidControl = false;
            actOutput = 1.0;
        }else if(!pidControl){
                pidControl = true;
                getPIDController().reset();
                getPIDController().enable();
                actOutput = -output;
        }else{

        actOutput = -output;
        }

        if (angle < -150.0)
        {
            actOutput = 0;
        }

        SmartDashboard.putDouble("Turret Voltage", actOutput);
        motor.set(actOutput);

    }

    /**
     * Converts the specified voltage from the potentiometer to degrees
     * 
     * @param voltage The value from the potentiometer that will be converted
     * to a degree value.
     * 
     * @return The voltage converted to degrees
     */
    public double convertVoltageToDeg(double voltage) {
        return (voltage-2.5) / 1.4 * -90;
        /*Wrong but close
         * Max Left: 0.9
        90 Left: 1.18
        Center:2.5
        90 Right:3.9
        Max Right: 4.2*/
    }

    /**
     * Resets the gyro, only to be called when the camera has returned that 
     * we are on target.
     */
    public void resetGyro() {
        gyro.reset();
    }
    
    public double getRawGyro(){
        return gyro.getAngle();
    }
    
    /**
     * This turns in the general direction down the field using the gyro.
     * The purpose of this is so that we are facing the correct general direction
     * so that if we have to shoot rapidly it goes towards our allies.
     */
    public void realignWithGyro() {
        double angleAway = gyro.getAngle();
        double curAngle = convertVoltageToDeg(pot.getVoltage());
        double targetAngle = curAngle + angleAway;

        if (targetAngle < 0) {
            targetAngle = (Math.abs(targetAngle) + 180);
        }
        targetAngle = targetAngle % 360;

        setSetpoint(targetAngle);
    }

    public void zeroAngleOffsets() {
        angleTrimZero = OI.getInstance().getRawAngleTrim();
    }
}
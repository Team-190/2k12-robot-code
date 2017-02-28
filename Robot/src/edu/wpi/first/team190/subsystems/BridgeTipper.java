/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.commands.bridgeTipper.DisableBridgeTipper;
import edu.wpi.first.team190.commands.bridgeTipper.TipperStatePosition;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Alex
 */
public class BridgeTipper extends PIDSubsystem {
    public Victor motor = new Victor(RobotMap.TIPPER_MOTOR);
    private AnalogChannel pot = new AnalogChannel(RobotMap.TIPPER_POT);
    private AnalogChannel currentSensor = new AnalogChannel(RobotMap.TIPPER_CURRENT);
    private DigitalInput bridgeSwitch = new DigitalInput(RobotMap.TIPPER_SWITCH);


    private DigitalInput limitSwitch = new DigitalInput(RobotMap.TIPPER_LIMIT);

    
    private double systemResistance = 4;
    private double v_speedcontroller = 0;
    private double MAX_CURRENT = 22;
    
    private double CURRENT_REDUCTION = 0.75;

    public static final double TIPPER_DOWN = 1.43;//-0.417;
    public static final double TIPPER_READY = 2.42;//0.436;//Ready to tip
    public static final double TIPPER_INBOUNDER = 3.19;//1.29;
    public static final double TIPPER_STOWED = 3.44;//1.50;
    
    public static final int FORCE_DOWN_STATE = 4;
    public static final int DOWN_STATE = 3;
    public static final int READY_STATE = 2;
    public static final int INBOUNDER_STATE = 1;
    public static final int STOWED_STATE = 0;

    private int state = 0;
    public void downState(){
        ++state;
        if(state>3)
            state = 3;
    }
    public void upState(){
        --state;
        if(state<0)
            state = 0;
    }
    public void setStatePosition(){
        switch(state){
            default:
            case STOWED_STATE:
                enable();
                setSetpoint(TIPPER_STOWED);
                break;
            case INBOUNDER_STATE:
                enable();
                setSetpoint(TIPPER_INBOUNDER);
                break;
            case READY_STATE:
                enable();
                setSetpoint(TIPPER_READY);
                break;
            case DOWN_STATE:
                enable();
                setSetpoint(TIPPER_DOWN);
                //setSetpoint(TIPPER_DOWN);
                break;
            case FORCE_DOWN_STATE:
                enable();
                setSetpoint(TIPPER_DOWN);
                break;
        }
    }

    public void resetState() {
        setState(STOWED_STATE);
    }

    public void setState(int state){
        if(state>4)
            this.state = 3;
        else if(state < 0)
            this.state = 0;
        else
            this.state = state;
    }
    
    


    public BridgeTipper() {
        super(-1.0, 0, 0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TipperStatePosition());
    }

    /**
     * Sets the setpoint to the position we want to move the tipper to.
     * 
     * @param position The position we want he tipper to move to. 
     */
    public void moveTipper(double position) {
        this.setSetpoint(position);
    }

    /**
     * Tells whether the tipper is on target or not.
     * 
     * @return Whether the bridge tipper has reached the setpoint or not.
     */
    public boolean onTarget() {
        return getPIDController().onTarget() || !getPIDController().isEnable();
    }

    protected double returnPIDInput() {
        return pot.getVoltage();
    }

    /**
     * Sets the motor to the amperage constrained output.
     * 
     * @param output The output of the PID loop, that needs to be constrained to
     * allow for amp limiting.
     */
    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Real Tipper", returnPIDInput());
         if(limitSwitch.get()){
             output = Math.max(output, 0);
         }
         if(state == DOWN_STATE){
             output = Math.min(output, 0.76);
         }
         motor.set(output);
    }


    private int downWait = 0;
    public void pushBridgeDown() {
       double output = getVoltage(getAmps(), MAX_CURRENT)/12;
        if(limitSwitch.get()){
             output = Math.max(output, 0);
        }
        if(output<0)
            downWait = 10;
        else
            --downWait;
        if(downWait>0)
           motor.set(0);
        else
            motor.set(output);
    }

    /**
     * Tells what the current sensor is currently reading in amps. Result is always
     * positive.
     * 
     * @return The absolute value of the amps measured by the current sensor.
     */
    public double getAmps() {
         return Math.abs(-(((currentSensor.getAverageVoltage() - 2.5)/* V */ * (1000/* mV / V */ / 40/* mV/A */)) + 0.561/* A (Bias) */));
    }

    /**
     * Constrains a given amperage to a given limit.
     * 
     * @param amps The amps we are trying to run at. Must be >= to 0.
     * @param desiredAmps The amps we want to limit ourselves to. Must be >= to 0.
     * @return The voltage required to run at our desired amps.
     */
    public double getVoltage(double amps, double desiredAmps) {
        double v_emf = v_speedcontroller - amps * systemResistance;
        v_speedcontroller = desiredAmps * systemResistance + v_emf;
        //v_speedcontroller = limit(-12, v_speedcontroller, 12);
        v_speedcontroller = limit(-12, v_speedcontroller, 12);
        return v_speedcontroller;
    }

    /**
     * Constrains a given value between a min and max values
     * 
     * @param min The lower limit for a value
     * @param val The value we need to constrain
     * @param max The maximum value the value can be
     * 
     * @return The constrained value
     */
    public double limit(double min, double val, double max) {
        return Math.min(max, Math.max(val, min));
    }
    
    /**
     * Returns whether the bridge tipper has reached the position
     */
    public boolean atPosition(){
        return onTarget();
    }   
    
    /**
     * Returns if the limit switch is triggered
     */
    public boolean atBridge() {
        return bridgeSwitch.get();
    }
}

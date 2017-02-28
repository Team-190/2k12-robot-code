/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.Logger;
import edu.wpi.first.team190.OI;
import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.commands.shooter.HoldShooterTemp;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg Granito
 */
public class Shooter extends PIDSubsystem implements BallPosition{

    public Jaguar m_motor = new Jaguar(RobotMap.SHOOTER_MOTOR);//TODO change to private
    private Encoder m_encoder = new Encoder(RobotMap.SHOOTER_ENCODER_A, RobotMap.SHOOTER_ENCODER_B);
    private double m_rate;
    //private DigitalInput EncoderA = new DigitalInput(RobotMap.SHOOTER_ENCODER_A);
    //private DigitalInput EncoderB = new DigitalInput(RobotMap.SHOOTER_ENCODER_B);
    private double virtualSetpoint = 0.0;
    private double speedTrimZero = 0.0;
    private double jkCount = 0.0;
    private double encoderPulses = 250.0;
    public boolean usePIDValue = true;
    
    
    
    
    public Shooter() {
        super("Shooter", 0.001, 0.0, 0); // TODO: TEST: un-official values
        //getPIDController().setInputRange(0.0, I); // TODO: TEST: undecided values
        getPIDController().setOutputRange(-1.0, 1.0); // TODO: TEST: Maybe -.04? maybe -1.0...
        getPIDController().setTolerance(5.0); // TODO: TEST: Unknown value
        
        
        m_encoder.setDistancePerPulse(1/encoderPulses);
        m_encoder.start();
        
        SmartDashboard.putDouble("FeedForward", 0.0);

        /**
         * This thread runs in the background, averaging the encoder distance
         * over a period of time since the get rate function that uses the fpga
         * is not accurate enough at high speeds.
         */
        new Thread() {

            public void run() {
                long timer= System.currentTimeMillis();
                m_encoder.reset();
                while (true) {
                    
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    m_rate = (-m_encoder.getDistance() / (double) (System.currentTimeMillis() - timer));//*1.0577;                    
                    m_encoder.reset();
                    
                    //System.out.println("A: " + EncoderA.get() + ", B: " + EncoderB.get());
                    timer = System.currentTimeMillis();
                    SmartDashboard.putDouble("Real Shooter Speed: ", (-m_rate * 1000 * 60));
                    
                    
                    
                }
            }
        }.start();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new HoldShooterTemp());
    }


    public void setSetpoint(double setpoint) {
        if(setpoint<=500)
            super.setSetpoint(0);
        else{
            super.setSetpoint(setpoint);
        }
    }

    /**
     * Runs the shooter at a specified speed
     *
     * @param speed The speed we want to run the shooter at
     */
    public void runAtSpeed(double speed) {
        setSetpoint(speed);
    }

    /**
     * Tells whether the shooter is at the correct speed
     *
     * @return Whether the shooter is up to speed
     */
    public boolean atDesiredSpeed() {
        if(getSetpoint() <= 0.0){
            return false;
        }else{
            double tolerance = Math.abs((getRPM() - getSetpoint())/getSetpoint());
            //System.out.println("Tolerance: "+ tolerance);
            return tolerance < 0.01;
        }
    }

    /**
     * Gives the PID the current RPM of the shooter
     *
     * @return Shooter RPM
     */
    protected double returnPIDInput() {
        
        return getRPM();
    }

    protected void usePIDOutput(double output) {
//        try {
//            //m_motor.set(output);
//            double motorVal = Math.max(0.0, output + (getPIDController().getSetpoint() * SmartDashboard.getDouble("FeedForward")));
//            m_motor.set(motorVal);
//            SmartDashboard.putDouble("MotorValue", motorVal);
//        } catch (NetworkTableKeyNotDefined ex) {
            //ex.printStackTrace();
        if (!usePIDValue){
            //System.out.println("Not Using PID Values");
            output=0;
        }
        output =(Math.max(output + (getPIDController().getSetpoint() * 0.000243), 0.0));
        //SmartDashboard.putDouble("Shooter Motor Value: ", output);
        m_motor.set(output);
        
//        }
    }

    /**
     * Runs the motor at a certain speed
     *
     * @param speed The speed we want to run the motor at
     */
    public void runMotor(double speed) {
        m_motor.set(speed);
    }

    /**
     * Gets the current RPM of the shooter
     *
     * @return The shooter's current RPM
     */
    public double getRPM() {
        return -m_rate /*
                 * rotations/ms
                 */ * 1000 /*
                 * ms/s
                 */ * 60 /*
                 * s/min
                 */;
    }

//    public double getBallQualityCompensation() {
//        double throttleValue = OI.getInstance().getThrottle();
//        double offsetValue = 0;
//
//        if (throttleValue < -.3) {
//            offsetValue = m_badOffset;
//        } else if (throttleValue > .3) {
//            offsetValue = m_goodOffset;
//        } else {
//            offsetValue = m_okOffset;
//        }
//        return offsetValue;
//    }
    
    
    
    
    private boolean shoot = false;
    public boolean hasBall() {
        return !shoot;
        
    }

    public void giveBall() {
            Logger.write("Turret Angle: " + CommandBase.turret.getCurrentAngle() +
                              ", ShooterSetPoint: " + CommandBase.shooter.getSetpoint() +
                              ", ShooterRPM: " + CommandBase.shooter.getRPM() +
                              ", Pixel Offset: " + Camera.getInstance().pixelOffset + 
                              ", Pixels From Top: " + Camera.getInstance().getLastYPix() +
                              ", Motor Speed: " + (m_motor.get()*12) + 
                              ", UsePID: " + usePIDValue +                     
                              "\r\n\r\n");
        shoot = false;
         
    }

    public void takeBall() {
    }

    public void shootNextBall() {
        shoot = true;
    }

    public void cancelShoot() {
        shoot = false;
    }
    
    public String getBallPositionName(){
        return "Shooter";
    }
    public String getSDName(){
        return "Shooter";
    }

    /**
     * distance in feet
     * Rpm in rpm
     */
    public static double distanceToRPM(double distance){
        return (540.1445166486404 + (248.7298765363163*distance) - 11.45725322637393*(distance*distance) +
                0.19063407596218743*(distance*distance*distance)) - 141.0-90;
    }

    public void setVirtualSetpoint(double setpoint){
        virtualSetpoint = setpoint;
    }

    public void zeroRPMOffsets(){
        speedTrimZero = OI.getInstance().getRawShooterSpeedTrim();
    }

    public void increaseJKCount() {
        jkCount++;
    }

    public void decreaseJKCount(){
        jkCount--;
    }

    public void runShooterAtVirtualSetpoint(){
        enable();
        double base = virtualSetpoint;
        double trimMult = 1.0 + (0.030303 * (-(OI.getInstance().getRawShooterSpeedTrim() - speedTrimZero)));
        double jkMult = 1.0 + (0.01 * jkCount);
        this.setSetpoint(virtualSetpoint* jkMult * trimMult);
        /*System.out.println("Virtual Setpoint :" + (virtualSetpoint * jkMult * trimMult)+
                ", Base: " + base + 
                ", jkCount: " + jkCount +
                ", speedTrimZero: " + speedTrimZero);*/
    }

    public void resetJKCount() {
        jkCount = 0.0;
    }
    
    public void resetPID(){
        this.getPIDController().reset();
        this.getPIDController().enable();
    }
}

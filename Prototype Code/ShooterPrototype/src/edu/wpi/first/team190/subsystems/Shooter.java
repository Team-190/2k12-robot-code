/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.commands.ShooterRunAtSpeed;
import edu.wpi.first.team190.commands.ShooterRunByStick;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author Alex
 */
public class Shooter extends PIDSubsystem {

    private static final double Kp = -(1/1200.0);
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    
    private Jaguar motor;
    private Encoder encoder;
    private double speed, rate;
    // Initialize your subsystem here
    public Shooter() {
        super("Shooter", Kp, Ki, Kd);

        this.getPIDController().setOutputRange(0.0, 1.0);
        
        motor = new Jaguar(RobotMap.shooterMotor);
        encoder = new Encoder(RobotMap.shooterEncoderA, RobotMap.shooterEncoderB);
        encoder.setDistancePerPulse(1/250.0);
        encoder.start();
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
        new Thread(){
          public void run() {
              while (true) {
                  encoder.reset();
                  long timer = System.currentTimeMillis();
                  try {
                      Thread.sleep(50);
                  } catch (InterruptedException ex) {
                      ex.printStackTrace();
                  } 
                  rate = encoder.getDistance()/(double)(System.currentTimeMillis()-timer);
              }
          }  
        }.start();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        
        //We want it to keep this
        setDefaultCommand(new ShooterRunAtSpeed());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return getRPM();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        motor.set(output);
    }
    
    public void stop(){
        motor.set(0.0);
    }
    public void runMotor(double speed){
        this.speed = speed;
        motor.set(speed);
    }
    public double getRPM(){
        return rate * 1000 * 60;
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    /**
     * This function is called once each time the robot enters operator control.
     */
    int currentSensorChannel = 1;
    int jaguarChannel = 1;
    int joystickChannel = 1;
    
    double systemResistance = .3;
    double v_speedcontroller = 0;
    double desiredAmps;
    
    AnalogChannel currentSensor = new AnalogChannel(currentSensorChannel);
    Victor speedController = new Victor(jaguarChannel);
    Joystick joy = new Joystick(joystickChannel);
    
    public void operatorControl() 
    {        
        while(true)
        {
            desiredAmps = 2.5 * joy.getY();
            SmartDashboard.putDouble("Desired Amps", desiredAmps);
            if (desiredAmps != 0) {
                speedController.set(getVoltage(getAmps(), desiredAmps)/12);
            } else {
                SmartDashboard.putDouble("Amps", getAmps());
                SmartDashboard.putDouble("Voltage", 0);
                v_speedcontroller = 0;
                speedController.set(0);
            }
            Timer.delay(.05);
        }

    }
    
    public double getAmps()
    {
        //TODO: Fix the actual math for converting the sensor value to amperes.
        return -(((currentSensor.getAverageVoltage()-2.5)*(1000/40))-1.5);
    }
    
    public double getVoltage(double amps, double desiredAmps)
    {   
        SmartDashboard.putDouble("Amps", amps);
        double v_emf =  v_speedcontroller - amps*systemResistance;
        SmartDashboard.putDouble("V_emf", v_emf);
        v_speedcontroller = desiredAmps*systemResistance + v_emf;
        v_speedcontroller = limit(-12, v_speedcontroller, 12);
        SmartDashboard.putDouble("Voltage", v_speedcontroller);
        return v_speedcontroller;
    }
    
    public double limit(double min, double val, double max){
        if (val < min) return min;
        else if (max < val) return max;
        else return val;
    }
}

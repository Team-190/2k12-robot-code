/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.cobra2k12.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Mitchell
 */
public class Collecter extends Subsystem{
    private Victor collecter = new Victor(RobotMap.collector);
    private SpeedController lower = new Victor(1);
    private SpeedController upper = new Victor(3);
    private SpeedController tongue = new Victor(4);
    private SpeedController shooter = new Victor(2);
    protected void initDefaultCommand() {
    }
    
    public void collect(){
        collecter.set(1);
        lower.set(1);
        upper.set(-1);
        tongue.set(1);
        shooter.set(1);
        
    }
        
    public void stopCollect(){
        collecter.set(0);
        lower.set(0);
        upper.set(0);
        tongue.set(0);
        shooter.set(0);
    }
    
}

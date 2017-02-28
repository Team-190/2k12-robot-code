/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Brendan
 */
public class RunTopCollectorViewSmartDashboard extends CommandBase{

    public RunTopCollectorViewSmartDashboard() {
        requires(bridgeTipper);
        SmartDashboard.putDouble("CollectorSpeed", 0.0);
    }
    protected void initialize() {
        try {
            bridgeTipper.motor.set(SmartDashboard.getDouble("CollectorSpeed"));
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
    }

    protected void execute() {
        try {
             bridgeTipper.motor.set(SmartDashboard.getDouble("CollectorSpeed"));
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        bridgeTipper.motor.set(0.0);
    }

    protected void interrupted() {
        end();
    }
    
}

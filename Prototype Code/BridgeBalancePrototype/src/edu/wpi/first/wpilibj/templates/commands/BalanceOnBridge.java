/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author Fred
 */
public class BalanceOnBridge extends CommandBase {
    public BalanceOnBridge(){
        requires(drivetrain);
    }    

    protected void initialize() {
        drivetrain.enable();
        drivetrain.setSetpoint(0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
        
    }

    protected void end() {
       drivetrain.disable();
    }

    protected void interrupted() {
        end();
    }
   
}

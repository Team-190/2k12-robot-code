/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.commands;

/**
 *
 * @author Mitchell
 */
public class InitializeSwerve extends CommandBase {
    public InitializeSwerve(){
        requires (driveTrain);
        
    }
    protected void initialize() {
    }

    protected void execute() {
       driveTrain.initializeSwerve();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        driveTrain.finishInitialize();
    }

    protected void interrupted() {
        end();
    }
    
}

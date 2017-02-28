/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.commands;

/**
 *
 * @author Mitchell
 */
public class TankDrive extends CommandBase{
    public TankDrive(){
        requires(driveTrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        double left = Math.abs(oi.getLeftY()) > 0.075 ? oi.getLeftY() : 0;
        double right = Math.abs(oi.getRightY()) > 0.075 ? oi.getRightY() : 0;
       
        driveTrain.driveTank(left, right);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
            driveTrain.driveTank(0, 0);
    }

    protected void interrupted() {
        end();
    }
    
}

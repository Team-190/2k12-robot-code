/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;


/**
 *
 * @author Fred
 */
public class FromInbounder extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        ballCount.addBall();
        ballCount.setIsInbounderBallTrue();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;


/**
 *
 * @author Fred
 */
public class FromGround extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        if(ballCount.getIsInbounderBall()){
            ballCount.setIsInbounderBallFalse();
        }
        else{
            ballCount.addBall();
        }
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
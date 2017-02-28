/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.commands;

/**
 *
 * @author Mitchell
 */
public class Collect extends CommandBase {
    public Collect(){
       requires(collecter);
    }
    protected void initialize() {
    }

    protected void execute() {
        collecter.collect();
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collecter.stopCollect();
        System.out.println("Stopping Collection...");
    }

    protected void interrupted() {
        end();
    }
    
}

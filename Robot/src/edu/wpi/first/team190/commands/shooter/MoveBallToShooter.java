/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.shooter;

import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.vm.FP;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;

/**
 *
 * @author Mitchell
 */
public class MoveBallToShooter extends CommandBase{
    /**
     * MoveBallToShooter 1 ball
     */
    public MoveBallToShooter(){
        this(false);
    }

    private boolean continuous;
    public MoveBallToShooter(boolean continuous){
        this.continuous = continuous;

        
    }
    protected void initialize() {
        shooter.shootNextBall();
    }

    protected void execute() {
        if(continuous){
            if(shooter.atDesiredSpeed()){
                shooter.shootNextBall();
            }
//            else
//                shooter.cancelShoot();
        }
    }

    protected boolean isFinished() {
        return !(continuous || !shooter.hasBall());
    }

    protected void end() {
        shooter.cancelShoot();
    }

    protected void interrupted() {
        end();
    }
}

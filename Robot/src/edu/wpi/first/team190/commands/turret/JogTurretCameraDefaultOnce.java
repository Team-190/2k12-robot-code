/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.turret;

import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.camera.NoTargetException;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.subsystems.Turret;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Greg
 */
public class JogTurretCameraDefaultOnce extends CommandBase{

    private boolean finished = false;
    private double lastSeen = -1;
    protected void initialize() {
        finished = false;
        turret.enable();
        
        try {
            double setPoint = Camera.getInstance().getTargetAngle();
               
            //setPoint = (setPoint);
            turret.enable();
            turret.setSetpoint(setPoint);
            lastSeen = Timer.getFPGATimestamp();
        } catch (NoTargetException ex) {
           
        }
        
        
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        //return true;
        try {
            return Camera.getInstance().isStationary() || finished;// && turret.onTarget();
        } catch (NoTargetException ex) {
            return true;
        }
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

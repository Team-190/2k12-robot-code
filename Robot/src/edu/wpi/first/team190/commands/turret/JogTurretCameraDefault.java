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
public class JogTurretCameraDefault extends CommandBase{

    private double lastSeen = -1;

    protected void initialize() {
        turret.enable();
    }

    protected void execute() {
        if (Camera.getInstance().seesTarget()){
        try {
            double setPoint = Camera.getInstance().getTargetAngle();
            setPoint = (setPoint + turret.getPosition()) /2;     
            //setPoint = (setPoint);
            turret.enable();
            turret.setSetpoint(setPoint);
            lastSeen = Timer.getFPGATimestamp();
            
            
        } catch (NoTargetException ex) {
           
        }
        }else{
             if(Timer.getFPGATimestamp() - lastSeen > 0.5){
                turret.disable();
                double desiredMotorVal = oi.getOperatorTwist();
                double position = turret.getPosition();
                if(Math.abs(desiredMotorVal) > 0.1){
                    if(Turret.LEFT_BOUND > position){
                        desiredMotorVal = Math.max(desiredMotorVal, 0.0);
                    }else if(Turret.RIGHT_BOUND < position){
                        desiredMotorVal = Math.min(desiredMotorVal, 0.0);
                    }
                    turret.motor.set(desiredMotorVal);
                }else{
                    turret.motor.set(0.0);
                }
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.motor.set(0.0);
        turret.setSetpoint(turret.getPosition());
        turret.enable();
    }

    protected void interrupted() {
        end();
    }

}

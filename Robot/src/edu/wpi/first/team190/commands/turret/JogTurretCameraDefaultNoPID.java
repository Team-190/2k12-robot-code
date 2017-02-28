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
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Brendan
 */
public class JogTurretCameraDefaultNoPID extends CommandBase{



    protected void initialize() {
        turret.disable();
 
    }

    protected void execute() {
            turret.disable();
            double delta = 0;
            double actOutput = 0;

            try {
                delta = Camera.getInstance().getAngleOffset();
                if(Math.abs(delta)<0.2){
                    actOutput = 0;
                }
                else
                    if (delta > 5.0)
                        actOutput = -1.0;
                    else if (delta < -5.0)
                        actOutput = 1.0;
                    else
                    try {
                        actOutput = SmartDashboard.getDouble("CameraP") * delta;
                    } catch (NetworkTableKeyNotDefined ex) {
                        actOutput = .15 * delta;
                }
            } catch (NoTargetException ex) {
                actOutput = 0;
            }

           turret.motor.set(actOutput);
      
    }

    protected boolean isFinished() {
        return false;
    }


    protected void interrupted() {
        end();
    }

    protected void end() {
        
        turret.motor.set(0.0);
        turret.setSetpoint(turret.getPosition());
        turret.enable();
    }

}

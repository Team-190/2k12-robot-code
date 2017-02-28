/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.turret;

import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.camera.NoTargetException;
import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Greg
 */
public class TurnToTargetLowPassFilterHybrid extends CommandBase {

    public TurnToTargetLowPassFilterHybrid(double timeout) {
        requires(turret);
        setTimeout(timeout);
    }

    protected void initialize() {
        turret.enable();
    }

    /**
     * Attempts to run the aim with camera methods in the turret system
     * 
     * Will return errors if there is no camera hooked up, and if the
     * camera doesn't see a target.
     */
    protected void execute() {
            try {
                turret.setSetpoint((turret.getPosition() + Camera.getInstance().getTargetAngle()) / 2);  
                //turret.setSetpoint(Camera.getInstance().getTargetAngle());
            } catch (NoTargetException ex) {
            }
    }

    /**
     * Ends the command.  Will return true if either of the camera exceptions
     * occur.
     * @return Whether or not the turret is aligned with the the target. 
     */
    protected boolean isFinished() {
        return isTimedOut();
    }

    /**
     * Resets the gyro on end so it can measure the the angle when aim with gyro
     * is called.
     */
    protected void end() {
        turret.resetGyro();
    }

    protected void interrupted() {
        end();
    }
}

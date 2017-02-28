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
 * @author Brendan
 */
public class TurnToTargetWithWait extends CommandBase{

    boolean failed = false;
    
    public TurnToTargetWithWait(double time) {
        setTimeout(time);
    }

    protected void initialize() {
        try {
            turret.setSetpoint((turret.getPosition() + Camera.getInstance().getTargetAngle()) / 2);
        } catch (NoTargetException ex) {
            failed = true;
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut() || failed;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}

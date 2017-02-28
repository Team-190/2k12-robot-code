/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190.commands.shooter;

import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.camera.NoTargetException;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.subsystems.Shooter;

/**
 *
 * @author Greg
 */
public class SetVirtualSetpoint extends CommandBase{

    public static final double CAMERA_BASED = -1.0;
    public static final double THROTTLE_BASED = -2.0;
    public static final double HYBRID_LOCATION = 2332;//was 2380.0; //2190.0;
    public static final double COOP_BUMP_CORNER = 2580.0; //was 2630.0; was 2766.0; //was 3000.0;
    public static final double ALLAINCE_BUMP_CORNER = 0.0;
    public static final double ALLIANCE_WALL = 3467.0;
    public static final double ALLAINCE_BRIDGE_HYBRID = 2560.0;//2450.0;

    private double type = 0.0;

    public SetVirtualSetpoint(double type) {
        this.type = type;
    }

    protected void initialize() {
        turret.zeroAngleOffsets();
        shooter.zeroRPMOffsets();
        shooter.resetJKCount();

        if(type == CAMERA_BASED){
            try {
            double dist = Camera.getInstance().getDistance();
                shooter.setVirtualSetpoint(Shooter.distanceToRPM(dist));
            } catch (NoTargetException ex) {
            }
        }else if(type == THROTTLE_BASED){
            shooter.setVirtualSetpoint((((-oi.operatorJoystick.getThrottle())+1)/2)*2200 + 1700);
        }else{
            shooter.setVirtualSetpoint(type);
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}

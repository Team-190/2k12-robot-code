/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.camera;

import edu.wpi.first.team190.commands.CommandBase;

/**
 *
 * @author Fred
 */
public class OffBoardCamera implements ICamera {

    

    public void start() {
    }

    public void stop() {
    }

    public Rectangle getRectangle() {
        return null;
    }

    public double getTimestamp() {
        return -1;
    }

    public double getTurretAngle() {
        return CommandBase.turret.getCurrentAngle();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.indicatorLights;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.subsystems.IndicatorLights;

/**
 *
 * @author Jeffrey
 */
public class ActivateStopLight extends CommandBase {
    
    public ActivateStopLight() {
        requires(lights);
    }
    
    protected void initialize() {
    }

    protected void execute() {
            lights.setLights(IndicatorLights.RED_LIGHT);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
            lights.setLights(0);
    }

    protected void interrupted() {
        end();
    }
    
}

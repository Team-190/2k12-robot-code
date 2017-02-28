/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.commands.indicatorLights;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.subsystems.IndicatorLights;

/**
 *
 * @author Mitchell
 */
public class CollectorIndicatorLights extends CommandBase{
    public CollectorIndicatorLights(){
        requires(lights);
    }

    protected void initialize() {
    }

    protected void execute() {
        int num = collector.getBallCount();
        if (num == 0) {
            lights.setLights(0);
        } else if (num == 1) {
            lights.setLights(IndicatorLights.GREEN_LIGHT);
        } else if (num == 2) {
            lights.setLights(IndicatorLights.GREEN_LIGHT|IndicatorLights.BLUE_LIGHT);
        } else if (num == 3) {
            lights.setLights(IndicatorLights.GREEN_LIGHT|IndicatorLights.BLUE_LIGHT|IndicatorLights.RED_LIGHT);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        lights.setLights(0);
    }

    protected void interrupted() {
        end();
    }
    
}

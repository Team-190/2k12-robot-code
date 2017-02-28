/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.commands.indicatorLights.CollectorIndicatorLights;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Mitchell
 */
public class IndicatorLights extends Subsystem {
    private static int mask(int num){
        return 1<<num;
    }
    
    public static final int RED_LIGHT_INDEX = 0;
    public static final int GREEN_LIGHT_INDEX = 1;
    public static final int BLUE_LIGHT_INDEX = 2;
    public static final int NUM_LIGHTS = 3;
    
    public static final int RED_LIGHT = mask(RED_LIGHT_INDEX);
    public static final int GREEN_LIGHT = mask(GREEN_LIGHT_INDEX);
    public static final int BLUE_LIGHT = mask(BLUE_LIGHT_INDEX);
    public Solenoid[] lights;

    public IndicatorLights() {
        lights = new Solenoid[NUM_LIGHTS];
        lights[GREEN_LIGHT_INDEX] = new Solenoid(RobotMap.GREEN_LIGHT_PORT);
        lights[BLUE_LIGHT_INDEX] = new Solenoid(RobotMap.BLUE_LIGHT_PORT);
        lights[RED_LIGHT_INDEX] = new Solenoid(RobotMap.RED_LIGHT_PORT);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new CollectorIndicatorLights());
    }
    
    public void setLights(int lightValues){
       /* for(int i = 0; i<NUM_LIGHTS; ++i){
            if((lightValues&mask(i))!=0)
                lights[i].set(true);
            else
                lights[i].set(false);
        }
        
        getTable().putBoolean("Green", lights[GREEN_LIGHT_INDEX].get());
        getTable().putBoolean("Red", lights[RED_LIGHT_INDEX].get());
        getTable().putBoolean("Blue", lights[BLUE_LIGHT_INDEX].get());*/
    }
    public String getType() {
        return "LightsSubsystem";
    }
}
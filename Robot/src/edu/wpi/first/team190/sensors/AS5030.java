/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.sensors;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;


/**
 *
 * @author Mitchell
 */
public class AS5030 implements PIDSource {

    private AnalogChannel sinChannel;
    private AnalogChannel cosChannel;
    public AS5030(AnalogChannel sinChannel, AnalogChannel cosChannel) {
        
        this.sinChannel = sinChannel;
        this.cosChannel = cosChannel;
    }
    public double getAngle(){
        double sin = (sinChannel.getAverageValue() - 430d) / 500d;
        double cos = (cosChannel.getAverageValue() - 430d) / 500d;
        double ang;
        //quad I
        if((cos>=0) && (sin>=0))
            ang = MathUtils.atan((Math.abs(sin / cos)));
        //quad II
        else if((cos <= 0) && (sin >= 0))
            ang = Math.PI - MathUtils.atan((Math.abs(sin / cos)));
        //quad III
        else if((cos <= 0) && (sin <= 0))
            ang = Math.PI +  MathUtils.atan((Math.abs(sin / cos)));
        else
            ang = 2*Math.PI - MathUtils.atan((Math.abs(sin / cos)));
        
        return ang % (2*Math.PI);
    }

    public double pidGet() {
        return getAngle();
    }
    
}

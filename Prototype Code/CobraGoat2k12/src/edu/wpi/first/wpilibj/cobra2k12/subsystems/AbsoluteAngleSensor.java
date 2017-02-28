package edu.wpi.first.wpilibj.cobra2k12.subsystems;

import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author dtjones
 */
public interface AbsoluteAngleSensor extends PIDSource {

    public double getAngle();

    public void calibrate();
}

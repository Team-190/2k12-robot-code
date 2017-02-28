/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.buttons;

import edu.wpi.first.team190.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 * @author alex
 */
public class OverridableJoystickButton extends JoystickButton {
    public static boolean MANUAL = true;
    public static boolean AUTOMATIC = false;
    
    private boolean manual;
    public OverridableJoystickButton(Joystick joystick, int button, boolean manual){
        super(joystick, button);
        this.manual = manual;
    }
    public boolean get() {
        return super.get() && OI.getInstance().getManualStatus()== this.manual;
    }
    
}
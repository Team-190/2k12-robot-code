
package edu.wpi.first.team190;

import edu.wpi.first.team190.commands.ShooterLockSpeed;
import edu.wpi.first.team190.commands.ShooterRunAtSpeed;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    // Process operator interface input here.
    Joystick gamepad = new Joystick(1);
    Button button = new JoystickButton(gamepad, 1);
    Button button2 = new JoystickButton(gamepad, 2);
    public OI () {
        button.whileHeld(new ShooterRunAtSpeed());
        //Allow speed to be set by gamepad and print to smart dash
        button2.whileHeld(new ShooterLockSpeed());
        //Lock speed if the button is no longer pressed. Stop when button pressed
        //button.whenPressed(new ShooterDoNothing());
        SmartDashboard.putDouble("Desired RPM", 0);
        SmartDashboard.putData(new ShooterRunAtSpeed());        
    }
    public double getVelocity (){
        return gamepad.getY();
    }
}


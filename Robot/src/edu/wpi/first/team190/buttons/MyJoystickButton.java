package edu.wpi.first.team190.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 * @author Mitchell
 */
public class MyJoystickButton extends JoystickButton{
    
    public MyJoystickButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);
    }
    
    public void whileHeldOnce(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = get();

            public void execute() {
                if (get()) {
                    if(!pressedLast){
                        command.start();
                        pressedLast = true;
                    }
                } else {
                    if (pressedLast) {
                        pressedLast = false;
                        command.cancel();
                    }
                }
            }
            protected void mystart() {
                Scheduler.getInstance().addButton(this);
            }
        }.mystart();
    }
}

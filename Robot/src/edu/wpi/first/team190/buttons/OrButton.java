/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.buttons;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Brendan
 */
public class OrButton extends Button{
    Button button1;
    Button button2;
    
    public OrButton(Button button1, Button button2) {
        this.button1 = button1;
        this.button2 = button2;
    }

    public boolean get() {
        return button1.get() || button2.get();
    }
    
}

package edu.wpi.first.wpilibj.cobra2k12;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.cobra2k12.commands.Collect;
import edu.wpi.first.wpilibj.cobra2k12.commands.InitializeSwerve;
import edu.wpi.first.wpilibj.cobra2k12.commands.SwerveDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    private Joystick leftJoystick;
    private Joystick rightJoystick;
    private Joystick gamePad;
    private Button swerveTrigger;
    private Button collectorTrigger;
    private Button initializeButton;

    public OI() {
        leftJoystick = new Joystick(2);
        rightJoystick = new Joystick(1);
        gamePad = new Joystick(3);
        swerveTrigger = new JoystickButton(leftJoystick, 1);
        collectorTrigger = new JoystickButton(leftJoystick, 2);
        initializeButton = new JoystickButton(leftJoystick, 11);
        swerveTrigger.whileHeld(new SwerveDrive());
        initializeButton.whileHeld(new InitializeSwerve());
        collectorTrigger.whileHeld(new Collect());
        
        gamePad = new Joystick(3);
        new JoystickButton(gamePad, 8).whileHeld(new SwerveDrive());
        new JoystickButton(gamePad, 4).whileHeld(new InitializeSwerve());
        new JoystickButton(gamePad, 6).whileHeld(new Collect());
        
        SmartDashboard.putBoolean("Enable Game Pad", true);
    }
    
    public double getLeftY(){
        try {
            if(SmartDashboard.getBoolean("Enable Game Pad"))
                return gamePad.getRawAxis(2)/2;
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
        return leftJoystick.getY();
    }
    public double getRightY(){
        try {
            if(SmartDashboard.getBoolean("Enable Game Pad"))
                return gamePad.getRawAxis(4)/2;
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
        return rightJoystick.getY();
    }
    public double getMagnitude(){
        try {
            if(SmartDashboard.getBoolean("Enable Game Pad"))
                return gamePad.getMagnitude()/2;
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
        return leftJoystick.getMagnitude();
    }
    public double getDirectionRadians(){
        try {
            if(SmartDashboard.getBoolean("Enable Game Pad"))
                return -gamePad.getDirectionRadians();
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
        return -leftJoystick.getDirectionRadians();
    }
}

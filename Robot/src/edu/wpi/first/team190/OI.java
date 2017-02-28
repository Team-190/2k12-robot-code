package edu.wpi.first.team190;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.team190.buttons.DriveTrigger;
import edu.wpi.first.team190.buttons.MyJoystickButton;
import edu.wpi.first.team190.buttons.OrButton;
//import edu.wpi.first.team190.camera.ChangeResolution2;
import edu.wpi.first.team190.commands.*;
import edu.wpi.first.team190.commands.bridgeTipper.ChangeTipperState;
import edu.wpi.first.team190.commands.collector.Collect;
import edu.wpi.first.team190.commands.collector.CollectorManualCommand;
import edu.wpi.first.team190.commands.collector.DefaultCollectorCommand;
import edu.wpi.first.team190.commands.collector.ResetCollector;
//import edu.wpi.first.team190.commands.driveTrain.DriveSwerveOneJoystick;
import edu.wpi.first.team190.commands.driveTrain.DriveSwerveViaJoysticks;
import edu.wpi.first.team190.commands.shooter.*;
import edu.wpi.first.team190.commands.turret.JogTurretCameraDefault;
import edu.wpi.first.team190.commands.turret.JogTurretCameraDefaultNoPID;
import edu.wpi.first.team190.commands.turret.JogTurretCameraDefaultOnce;
import edu.wpi.first.team190.commands.turret.JogTurretViaJoystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.AnalogIOButton;

public class OI {
    
    private static OI instance;

    public SendableChooser auton;
    public Joystick driverGamePad = new Joystick(RobotMap.GAMEPAD_PORT);
    public Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_PORT);

    public AnalogIOButton shooterSpeedJogSlider = new AnalogIOButton(1);
    public AnalogIOButton angleJogSlider = new AnalogIOButton(2);
    
    private OI(){
        
        new MyJoystickButton(operatorJoystick, 1).whileHeldOnce(new FireSequence());
        new JoystickButton(operatorJoystick, 2).whileHeld(new JogTurretCameraDefault());//JogTurretCameraDefault());
        new JoystickButton(operatorJoystick, 3).whileHeld(new JogTurretViaJoystick());
        new JoystickButton(operatorJoystick, 4).whileHeld(new SetVirtualSetpoint(SetVirtualSetpoint.CAMERA_BASED));
        new JoystickButton(operatorJoystick, 5).whileHeld(new SetVirtualSetpoint(SetVirtualSetpoint.THROTTLE_BASED));
        
        new MyJoystickButton(operatorJoystick, 6).whileHeld(new JogTurretCameraDefaultOnce());
        
        //new MyJoystickButton(operatorJoystick, 11).whileHeld(new resetUsePID());
        
        
        new JoystickButton(operatorJoystick, 7).whileHeld(new CollectorManualCommand(1.0));
        new JoystickButton(operatorJoystick, 9).whileHeld(new CollectorManualCommand(-1.0));

        new JoystickButton(operatorJoystick, 8).whenPressed(new RunShooterAtVirtualSetPoint());
        new JoystickButton(operatorJoystick, 10).whenPressed(new StopShooter());
        new JoystickButton(operatorJoystick, 12).whenPressed(new ResetCollector());

        //SmartDashboard.putData(new TipBridge());

        new DigitalIOButton(1).whenPressed(new SetVirtualSetpoint(3900)); //guess
        new DigitalIOButton(2).whenPressed(new JKCountIncrease());
        new DigitalIOButton(3).whenPressed(new SetVirtualSetpoint(SetVirtualSetpoint.ALLIANCE_WALL)); 
        new DigitalIOButton(4).whenPressed(new JKCountDecrease());
        new DigitalIOButton(6).whenPressed(new SetVirtualSetpoint(SetVirtualSetpoint.ALLAINCE_BRIDGE_HYBRID)); //badball No purpose
        new DigitalIOButton(8).whenPressed(new SetVirtualSetpoint(SetVirtualSetpoint.HYBRID_LOCATION));
        new DigitalIOButton(13).whenPressed(new SetVirtualSetpoint(SetVirtualSetpoint.COOP_BUMP_CORNER));
        new DigitalIOButton(15).whenPressed(new SetVirtualSetpoint(SetVirtualSetpoint.ALLAINCE_BUMP_CORNER));

        new DigitalIOButton(7).whenPressed(new ChangeTipperState(false));
        new DigitalIOButton(5).whenPressed(new ChangeTipperState(true));
        
        new JoystickButton(driverGamePad, 8).whileHeld(new DriveSwerveViaJoysticks());
        //new JoystickButton(driverGamePad, 1).whileHeld(new DriveSwerveOneJoystick());
        //new JoystickButton(driverGamePad, 6).whileHeld(new Collect());
        new OrButton(new JoystickButton(driverGamePad, 6),
                    new DriveTrigger()).whileHeld(new Collect());
        
        new JoystickButton(driverGamePad, 5).whenPressed(new ChangeTipperState(true));
        new JoystickButton(driverGamePad, 7).whenPressed(new ChangeTipperState(false));
        
        
        auton = new SendableChooser();
        auton.addDefault("Coop Bridge", new CoopBridgeAutonomous());
        auton.addObject("Do Nothing", new DoNothingAutonomous());
        auton.addObject("Shoot from Key", new ShootAtKeyAutonomous());
        SmartDashboard.putData("Autonomous Chooser", auton);
        
        
    }

    public double getRawShooterSpeedTrim(){
        try {
            return -(DriverStation.getInstance().getEnhancedIO().getAnalogIn(1));
        } catch (EnhancedIOException ex) {
            return 0;
        }
    }
    public double getRawAngleTrim(){
        try {
            return -(DriverStation.getInstance().getEnhancedIO().getAnalogIn(2));
        } catch (EnhancedIOException ex) {
            return 0;
        }
    }

    public static OI getInstance(){
        return instance == null? instance = new OI() : instance;
    }

    public double getLeftStickY(){
        //return -driverGamePad.getRawAxis(2);
        double y = driverGamePad.getRawAxis(2);
        return -(y*y) * (Math.abs(y)/y);
    }

    public double getLeftStickX(){
        return driverGamePad.getRawAxis(1);
    }

    public double getLeftStickAngle(){
        return MathUtils.atan2(getLeftStickX(),getLeftStickY());
    }

    public double getLeftStickMagnitude(){
        double x = getLeftStickX();
        //double y = getLeftStickY();
        double y=Math.sqrt(Math.abs(getLeftStickY()));
        //return Math.sqrt((x*x)+(y*y));//used to be the square root
        return (x*x)+(y*y);//used to be the square root
    }

    public double getRightStickY(){
        //return -driverGamePad.getRawAxis(4);
        double y = driverGamePad.getRawAxis(4);
        return -(y*y) * (Math.abs(y)/y);
    }
    
    public double getRightStickX(){
        return driverGamePad.getRawAxis(3);
    }

    public double getRightStickAngle(){
        //return MathUtils.atan2(getRightStickX(), getRightStickY());
    
        double ang = MathUtils.atan2(getRightStickX(), getRightStickY());
        if (getLeftStickY()<0)
            return (ang + Math.PI);
        else
            
            
            
            
            
            
            
            
            return ang;
    }

    public double getRightStickMagnitude(){
        double x = getRightStickX();
        double y = getRightStickY();
        return Math.sqrt((x*x)+(y*y));
    }

    public boolean getManualStatus() {
        return SmartDashboard.getBoolean("Manual Mode", false);
    }
    
    public double getOperatorTwist(){
        return -operatorJoystick.getTwist();
    }
}

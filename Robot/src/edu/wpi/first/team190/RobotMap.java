package edu.wpi.first.team190;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // TODO: STARTED: Fill in RobotMap, rename to proper sensors (leave to Alex and wiring people)

    //Collector Motors and Sensors
    public static final int COLLECTOR_S1_MOTOR = 4;
    public static final int COLLECTOR_S1_SENSOR = 2;

    public static final int COLLECTOR_S2_MOTOR = 5;
    public static final int COLLECTOR_S2_SENSOR = 4;

    public static final int COLLECTOR_S3_MOTOR = 6;
    public static final int COLLECTOR_S3_SENSOR = 3;

    //Tipper Motors and Sensor
    public static final int TIPPER_MOTOR = 8;
    public static final int TIPPER_POT = 7;
    public static final int TIPPER_CURRENT = 3;
    public static final int TIPPER_SWITCH = 5;
    public static final int TIPPER_LIMIT = 13;

    //Driveline Motors and Sensors
    public static final int LEFT_DRIVE_MOTOR = 1;
    public static final int RIGHT_DRIVE_MOTOR = 2;
    
    public static final int LEFT_DRIVE_ENCODER_A = 6;
    public static final int LEFT_DRIVE_ENCODER_B = 7;
    
    public static final int RIGHT_DRIVE_ENCODER_A = 8;
    public static final int RIGHT_DRIVE_ENCODER_B = 9;
    

    public static final int SWERVE_MOTOR = 3;
    public static final int SWERVE_SENSOR = 5;

    //Shooter Motors and Sensors
    public static final int SHOOTER_MOTOR = 7;
    public static final int SHOOTER_ENCODER_A = 11;
    public static final int SHOOTER_ENCODER_B = 12;
    
    public static final int CAMERA_LIGHTS = 4;

    public static final int TURRET_MOTOR = 9;
    public static final int TURRET_SENSOR = 2;
    public static final int TURRET_GYRO = 1;
      
    //SmartDashboard string Constants
    public static final String DRIVETRAIN_ANGLE_OFFSET = "Angle Offset";

    //Lights
    public static final int GREEN_LIGHT_PORT = 2;
    public static final int BLUE_LIGHT_PORT = 3;
    public static final int RED_LIGHT_PORT = 1;

    //OI Constants
    public static final int GAMEPAD_PORT = 1;
    public static final int GAMEPAD_SWERVE_BUTTON = 8;
    public static final int OPERATOR_PORT = 2;
    
}
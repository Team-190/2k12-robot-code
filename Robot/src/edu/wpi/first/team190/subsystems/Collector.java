package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.team190.commands.collector.CollectorSensorMonitorCommand;
import edu.wpi.first.team190.commands.collector.DefaultCollectorCommand;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Mitchell
 */
public class Collector extends Subsystem{
    public static final double TIMEOUT = 2.0;//timeout for no change in seconds
    
    public static final double SHOOTER_FEED_SPEED = 0.35;
    public static final double COLLECT_SPEED = 1.0;
    public static final double UP_TRANSFER_SPEED = .27;
    public static final double DOWN_SPEED = .35;
    
    public static final double SHOOTER_TRANSFER_TIME = 0.5;//time between the ball leaving the last sensor and hitting the shooter
    public static final double COLLECTOR_TRANSFER_TIME = 0.5;//time between the ball going below the lowest sensor and leaving the stage
    
    {
        if(CommandBase.shooter==null)
            throw new RuntimeException("The shooter must be created before the collector");
    }
    
    final BallPosition PShooter = CommandBase.shooter;
    final BallPosition PPreShooter = new BallPosition.ShooterPre();
    final BallPosition PS3 = new BallPosition.Default("Stage 3 Position", "S3");
    final BallPosition PS2_S3 = new BallPosition.Intermediate("Intermediate 2-3 Position", "S23");
    final BallPosition PS2 = new BallPosition.Default("Stage 2 Position", "S2");
    final BallPosition PS1_S2 = new BallPosition.Intermediate("Intermediate 1-2 Position", "S12");
    final BallPosition PS1 = new BallPosition.Default("Stage 1 Position", "S1");
    final BallPosition PPostStart = new BallPosition.StartPost();
    final BallPosition.Start PStart = new BallPosition.Start();
    final BallPosition[] ballPositions = {PStart, PPostStart, PS1, PS1_S2, PS2, PS2_S3, PS3, PPreShooter, PShooter};
    
    final BallStage S3 = new BallStage("Stage 3", new Victor(RobotMap.COLLECTOR_S3_MOTOR), false, RobotMap.COLLECTOR_S3_SENSOR, PS2, PS2_S3, PS3, PPreShooter, PShooter, 0.17, SHOOTER_FEED_SPEED, DOWN_SPEED);
    final BallStage S2 = new BallStage("Stage 2", new Jaguar(RobotMap.COLLECTOR_S2_MOTOR), false, RobotMap.COLLECTOR_S2_SENSOR, PS1, PS1_S2, PS2, PS2_S3, new CompositeBallPosition(PS3, PPreShooter), UP_TRANSFER_SPEED, UP_TRANSFER_SPEED, DOWN_SPEED);
    final BallStage S1 = new BallStage("Stage 1", new Victor(RobotMap.COLLECTOR_S1_MOTOR), true, RobotMap.COLLECTOR_S1_SENSOR, PStart, PPostStart, PS1, PS1_S2, PS2, COLLECT_SPEED, COLLECT_SPEED, DOWN_SPEED);
    
    
    /*
     * NOTE!!! Do note use in a posistion where the Ball position gives or takes a ball
     */
    private static class CompositeBallPosition implements BallPosition{
        private BallPosition lower;
        private BallPosition upper;
        public CompositeBallPosition(BallPosition lower, BallPosition upper){
            this.lower = lower;
            this.upper = upper;
        }

        public boolean hasBall() {
            return lower.hasBall() || upper.hasBall();
        }

        public void giveBall() {
        }

        public void takeBall() {
        }

        public String getBallPositionName() {
            return "CompositeBallPosition["+lower.getBallPositionName()+", "+upper.getBallPositionName()+"]";
        }

        public String getSDName() {
            return "";
        }
    }
    
    
    final BallStage[] ballStages = {S1, S2, S3};
    
    
    
    protected void initDefaultCommand() {
        new CollectorSensorMonitorCommand().start();
        setDefaultCommand(new DefaultCollectorCommand(true));
    }
    
    public int getBallCount(){
        return (PPreShooter.hasBall()?1:0) + (PS3.hasBall()?1:0) + (PS2_S3.hasBall()?1:0) + (PS2.hasBall()?1:0) + (PS1_S2.hasBall()?1:0) + (PS1.hasBall()?1:0);
    }
    
    public void setCollect(int num){
        PStart.setCollect(num);
    }
    public void stopCollecting(){
        PStart.stopCollect();
    }
    public boolean isCollecting(){
        return PStart.hasBall();
    }
    
    public void defaultRunUp(){
        S1.defaultRunUp();
        S2.defaultRunUp();
        S3.defaultRunUp();
    }
    
    public void defaultRunDown(){
        S1.defaultRunDown();
        S2.defaultRunDown();
        S3.defaultRunDown();
    }
    
    public void stop(){
        S1.stop();
        S2.stop();
        S3.stop();
    }
    
    public void processSensor(){
        S1.processSensor();
        S2.processSensor();
        S3.processSensor();
        /*
        for(int i = 0; i<ballPositions.length; ++i)
            getTable().putBoolean(ballPositions[i].getSDName(), ballPositions[i].hasBall());
        for(int i = 0; i<ballStages.length; ++i)
            getTable().putDouble(ballStages[i].getStageName(), ballStages[i].getSpeed());
        getTable().putInt("Ball Count", getBallCount());*/
    }
    
    public void setSpeed(double speed){
        S1.setSpeed(speed);
        S2.setSpeed(speed/1.2);
        S3.setSpeed(speed/1.2);
    }
    
    
    public String getType() {
        return "CollectorSubsystem";
    }

    public void reset() {
        PStart.takeBall();
        PS1_S2.takeBall();
        PS2_S3.takeBall();
        PPreShooter.takeBall();
    }
    
}

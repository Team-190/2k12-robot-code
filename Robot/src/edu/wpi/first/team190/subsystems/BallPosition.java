/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.team190.Logger;
import edu.wpi.first.team190.camera.Camera;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Mitchell
 */
public interface BallPosition {


    public boolean hasBall();

    public void giveBall();

    public void takeBall();
    
    public String getBallPositionName();
    
    public String getSDName();

    public class Default implements BallPosition {

        private boolean hasBall = false;
        private String name;
        private String sdName;
        private double lastStateChange;

        public Default(String name, String sdName) {
            this.name = name;
            this.sdName = sdName;
            lastStateChange = 0;
        }

        public boolean hasBall() {
            return hasBall;
        }

        private void setHasBall(boolean hasBall) {
            if (this.hasBall != hasBall) {
                this.hasBall = hasBall;
                lastStateChange = Timer.getFPGATimestamp();
            }
        }

        public void giveBall() {
            setHasBall(true);
        }

        public void takeBall() {
            setHasBall(false);
        }

        public String getSDName(){
            return sdName;
        }

        public String getBallPositionName(){
            return name;
        }

        public String toString() {
            return getBallPositionName() + " " + (hasBall ? "has ball" : " does not have ball");
        }
    }


    public class Intermediate implements BallPosition {

        private boolean hasBall = false;
        private String name;
        private String sdName;
        private double lastStateChange;

        public Intermediate(String name, String sdName) {
            this.name = name;
            this.sdName = sdName;
            lastStateChange = 0;
        }

        public boolean hasBall() {
            if(Timer.getFPGATimestamp()-lastStateChange>2.0)
                takeBall();
            return hasBall;
        }

        private void setHasBall(boolean hasBall) {
            if (this.hasBall != hasBall) {
                this.hasBall = hasBall;
                lastStateChange = Timer.getFPGATimestamp();
            }
        }

        public void giveBall() {
            setHasBall(true);
        }

        public void takeBall() {
            setHasBall(false);
        }

        public String getSDName(){
            return sdName;
        }

        public String getBallPositionName(){
            return name;
        }

        public String toString() {
            return getBallPositionName() + " " + (hasBall ? "has ball" : " does not have ball");
        }
    }

    public class Start extends Default {
        public static final int INFINITE = -1;

        public Start() {
            super("Start Ball Position", "Start");
        }
        private int num = 0;

        public boolean hasBall() {
            return (num != 0 && CommandBase.collector.getBallCount() < 3);
        }

        public void giveBall() {
        }

        public void takeBall() {
            if (num > 0) {
                --num;
            }
        }

        /**
         * Set the number of balls to collect before stopping -1 will collect
         * forever
         */
        public void setCollect(int num) {
            this.num = num;
        }

        public void stopCollect() {
            num = 0;
        }
        public int getNum(){
            return num;
        }
    }
    

    public static class ShooterPre implements BallPosition{
        private static final double NO_BALL = -1;
        double lastBallGain = NO_BALL;

        public ShooterPre() {
            super();
            System.out.println("Intializing FileWriter");
            
        }

        public boolean hasBall() {
            if(lastBallGain==NO_BALL)
                return false;
            if(Timer.getFPGATimestamp()-lastBallGain>Collector.SHOOTER_TRANSFER_TIME){
                takeBall();
                CommandBase.shooter.giveBall();
                System.out.println("Occurance 1");
                return false;
            }
            return true;
        }

        public void giveBall() {
            lastBallGain = Timer.getFPGATimestamp();

        }

        public void takeBall() {
            lastBallGain = NO_BALL;
        }

        public String getBallPositionName() {
            return "Pre Shooter Position";
        }
        public String getSDName(){
            return "PShooter";
        }

        
    }
    
    public static class StartPost implements BallPosition {
        private static final double NO_BALL = -1;
        double lastBallGain = NO_BALL;
        
        
        public boolean hasBall() {
            if(lastBallGain==NO_BALL)
                return false;
            if(Timer.getFPGATimestamp()-lastBallGain>Collector.COLLECTOR_TRANSFER_TIME){
                lastBallGain = NO_BALL;
                CommandBase.collector.PStart.giveBall();
                return false;
            }
            return true;
        }

        public void giveBall() {
            lastBallGain = Timer.getFPGATimestamp();
        }

        public void takeBall() {
            lastBallGain = NO_BALL;
            CommandBase.collector.PStart.takeBall();
        }
        

        public String getBallPositionName() {
            return "Post Start Position";
        }
        public String getSDName(){
            return "PStart";
        }

    }
}

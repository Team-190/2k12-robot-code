/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Mitchell
 */
public class BallStage {

    private String name;
    private SpeedController motor;
    private boolean motorInverted;
    private DigitalInput sensor;
    private BallPosition previous;
    private BallPosition previousIntermediate;
    private BallPosition current;
    private BallPosition nextIntermediate;
    private BallPosition next;
    private double feedUpSpeed;
    private double receiveSpeed;
    private double downSpeed;

    public BallStage(String name, SpeedController motor, boolean motorInverted, int sensorPin, BallPosition previous, BallPosition previousIntermediate, BallPosition current, BallPosition nextIntermediate, BallPosition next, double receiveSpeed, double feedUpSpeed, double downSpeed) {
        this.name = name;
        this.motor = motor;
        this.motorInverted = motorInverted;
        sensor = new DigitalInput(sensorPin);
        this.previous = previous;
        this.previousIntermediate = previousIntermediate;
        this.current = current;
        this.nextIntermediate = nextIntermediate;
        this.next = next;
        this.feedUpSpeed = feedUpSpeed;
        this.receiveSpeed = receiveSpeed;
        this.downSpeed = downSpeed;
    }
    
    public double getSpeed(){
        if (motorInverted) {
            return -motor.get();
        } else {
            return motor.get();
        }
    }

    public boolean isForward() {
            return getSpeed() >= 0;
    }
    private boolean previousSensorValue = false;

    public void processSensor() {
        boolean newSensorValue = !sensor.get();
        //TODO remove
        if (previousSensorValue != newSensorValue) {
            if (newSensorValue) {//ball was gained
                if (isForward()) {
                    previousIntermediate.takeBall();
                } else {
                    nextIntermediate.takeBall();
                }
                current.giveBall();
            } else {//ball was lost
                current.takeBall();
                if (isForward()) {
                    nextIntermediate.giveBall();
                } else {
                    previousIntermediate.giveBall();
                }
            }
        }
        previousSensorValue = newSensorValue;
    }

    public void setSpeed(double speed) {
        if (motorInverted) {
            motor.set(-speed);
        } else {
            motor.set(speed);
        }
    }

    public void feedBallUp() {
        setSpeed(feedUpSpeed);
    }

    public void acceptBallUp() {
        setSpeed(receiveSpeed);
    }

    public void feedBallDown() {
        setSpeed(-downSpeed);
    }

    public void acceptBallDown() {
        setSpeed(-downSpeed);
    }

    public void stop() {
        setSpeed(0.0);
    }
    private double lastDefault = 0;

    public void defaultRunUp() {
        //if(previousIntermediate.hasChangedWithin(TIMEOUT) || current.hasChangedWithin(TIMEOUT) || nextIntermediate.hasChangedWithin(TIMEOUT) || lastDefault-Timer.getFPGATimestamp()>TIMEOUT){//timeout
        if (current.hasBall()) {
            if (!nextIntermediate.hasBall() && !next.hasBall()) {
                feedBallUp();
            } else {
                stop();
            }
        } else if (nextIntermediate.hasBall()) {
            if (!next.hasBall()) {
                feedBallUp();
            } else {
                stop();
            }
        } else {
            if (previous.hasBall() || previousIntermediate.hasBall()) {
                acceptBallUp();
            } else {
                stop();
            }
        }
        //}
        //else
        //    stop();
        lastDefault = Timer.getFPGATimestamp();
    }

    public void defaultRunDown() {
        if (!previousIntermediate.hasBall() && !previous.hasBall()) {
            feedBallDown();
        } else if (next.hasBall() || nextIntermediate.hasBall()) {
            acceptBallDown();
        } else {
            stop();
        }
    }

    public String getStageName(){
        return name;
    }
    public String toString() {
        return getStageName();
    }
}
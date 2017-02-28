
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.FromGround;
import edu.wpi.first.wpilibj.templates.commands.FromInbounder;
import edu.wpi.first.wpilibj.templates.commands.LeavingRobot;

/**
 * To keep tracking of the balls currently contained in the robot.
 */
public class BallCounter extends Subsystem {
    private class DIButton extends Button{
        DigitalInput input;
        DIButton(DigitalInput input){
            this.input = input;
        }

        public boolean get() {
            return !input.get();
        }
        
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private int ballsContained = 0;

    
    private boolean isInbounderBall;
    private DigitalInput ballLeave = new DigitalInput(1);
    private DigitalInput ballFromGround = new DigitalInput(2);
    private DigitalInput ballFromInbounder = new DigitalInput(3);
    private Button leaveButton= new DIButton(ballLeave);
    private Button groundButton= new DIButton(ballFromGround);
    private Button inbounderButton = new DIButton(ballFromInbounder);

    
    public void initDefaultCommand() {
        inbounderButton.whenPressed(new FromInbounder());
        groundButton.whenPressed(new FromGround());
        leaveButton.whenPressed(new LeavingRobot());
    }
    public void addBall(){
        ballsContained += 1;
    }
    public void removeBall(){
        ballsContained -=1;
    }
    
    public int getBallsContained(){
        return ballsContained;
    }
    public boolean getIsInbounderBall() {
        return isInbounderBall;
    }

    public void setIsInbounderBallFalse() {
        isInbounderBall = false;
    }
    public void setIsInbounderBallTrue() {
        isInbounderBall = true;
    }
}


package edu.wpi.first.wpilibj.robottesting.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.robottesting.Assertion;
import edu.wpi.first.wpilibj.robottesting.AssertionFailure.Level;
import edu.wpi.first.wpilibj.robottesting.OneShotAssertion;
import edu.wpi.first.wpilibj.robottesting.ProblemHandler;
import edu.wpi.first.wpilibj.robottesting.ProblemManager;


/**
 * A Command which asserts that the voltage of the battery is within appropriate
 * limits.
 *
 * @author pmalmsten
 */
public class TestBatteryVoltage extends Command {
    private ProblemHandler m_problemHandler;
    private Assertion m_batteryVoltageNotCriticallyLow;
    private Assertion m_batteryVoltageNotLow;
    private Assertion m_batteryVoltageNotTooHigh;


    /**
     * Create a new TestBatteryVoltage command which reports AssertionFailures
     * to the given ProblemHandler object.
     *
     * @param handler The ProblemHandler to inform of AssertionFailures.
     */
    public TestBatteryVoltage(ProblemHandler handler) {
        m_problemHandler = handler;
    }

     /**
     * Create a new TestBatteryVoltage command which reports AssertionFailures
     * to the singleton ProblemManager instance.
     */
    public TestBatteryVoltage() {
        this(ProblemManager.getInstance());
    }

    protected void initialize() {
        m_batteryVoltageNotCriticallyLow = new OneShotAssertion("Bat. Crit. Low", Level.ERROR, m_problemHandler) {

            protected void evaluate() throws Assertion.AssertionFailureException {
                double voltage = DriverStation.getInstance().getBatteryVoltage();
                assertTrue(voltage >= 11, "Battery voltage critically low: " + voltage);
            }
        };

        m_batteryVoltageNotLow = new OneShotAssertion("Bat. Low", Level.WARNING, m_problemHandler) {

            protected void evaluate() throws Assertion.AssertionFailureException {
                double voltage = DriverStation.getInstance().getBatteryVoltage();
                assertTrue(DriverStation.getInstance().getBatteryVoltage() >= 12.1, "Battery voltage low: " + voltage);
            }
        };

        m_batteryVoltageNotTooHigh = new OneShotAssertion("Bat. High", Level.WARNING, m_problemHandler) {

            protected void evaluate() throws Assertion.AssertionFailureException {
                double voltage = DriverStation.getInstance().getBatteryVoltage();
                assertTrue(DriverStation.getInstance().getBatteryVoltage() <= 14, "Battery voltage high: " + voltage);
            }
        };
    }

    protected void execute() {
        m_batteryVoltageNotCriticallyLow.check();
        m_batteryVoltageNotLow.check();
        m_batteryVoltageNotTooHigh.check();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
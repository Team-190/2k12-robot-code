package edu.wpi.first.wpilibj.robottesting.commands;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.robottesting.Assertion;
import edu.wpi.first.wpilibj.robottesting.AssertionFailure.Level;
import edu.wpi.first.wpilibj.robottesting.ProblemHandler;
import edu.wpi.first.wpilibj.robottesting.ProblemManager;

/**
 * A Command which applies power to a mechanism and asserts that it achieves a 
 * minimum expected velocity.
 * 
 * @author pmalmsten
 */
public class TestChangeVelocity extends Command {
    private String m_name;
    private SpeedController m_output;
    private PIDSource m_input;
    private double m_powerToApply;
    private double m_expectedMinVelocity;
    private ProblemHandler m_problemHandler;

    private Assertion m_goingFastEnough;

    /**
     * Creates a TestChangeVelocity command which applies the given output power
     * setting to the given motor and asserts that its attached mechanism achieves
     * an expected minimum velocity. At the end of the given duration, power will
     * be cut to the given device and the given velocity input will be sampled
     * to determine whether the expected minimum velocity was achieved.
     *
     * @param name The name of this test.
     * @param system The subsystem this command should require when running.
     * @param output The output device to apply power to.
     * @param input The velocity sensor to measure.
     * @param powerToApply The output power setting to apply to the given
     *  output device, from -1 to 1.
     * @param expectedMinVelocity The minimum expected velocity to be achieved
     *  by the associated mechanism.
     * @param duration The duration for which to apply the indicated output power.
     *  At the end of this duration, power will be cut and the mechanism's velocity
     *  sensor will be sampled to determine whether the expected minimum
     *  velocity was achieved.
     * @param handler The handler to which assertion failure messages generated
     *  by this test will be reported.
     */
    public TestChangeVelocity(String name,
                              Subsystem system,
                              SpeedController output,
                              PIDSource input,
                              double powerToApply,
                              double expectedMinVelocity,
                              double duration,
                              ProblemHandler handler) {
        requires(system);

        m_name = name;
        m_input = input;
        m_output = output;
        m_powerToApply = powerToApply;
        m_expectedMinVelocity = expectedMinVelocity;
        m_problemHandler = handler;

        setTimeout(duration);
    }

    /**
     * Creates a TestChangeVelocity command which applies the given output power
     * setting to the given motor and asserts that its attached mechanism achieves
     * an expected minimum velocity. At the end of the given duration, power will
     * be cut to the given device and the given velocity input will be sampled
     * to determine whether the expected minimum velocity was achieved.
     *
     * Any AssertionFailures generated during this test will be reported to the
     * singleton {@link ProblemManager} instance.
     *
     * @param name The name of this test.
     * @param system The subsystem this command should require when running.
     * @param output The output device to apply power to.
     * @param input The velocity sensor to measure.
     * @param powerToApply The output power setting to apply to the given
     *  output device, from -1 to 1.
     * @param expectedMinVelocity The minimum expected velocity to be achieved
     *  by the associated mechanism.
     * @param duration The duration for which to apply the indicated output power.
     *  At the end of this duration, power will be cut and the mechanism's velocity
     *  sensor will be sampled to determine whether the expected minimum
     *  velocity was achieved.
     */
    public TestChangeVelocity(String name,
                              Subsystem system,
                              SpeedController output,
                              PIDSource input,
                              double powerToApply,
                              double expectedMinVelocity,
                              double duration) {
        this(name, system, output, input, powerToApply, expectedMinVelocity, duration, ProblemManager.getInstance());
    }

    protected void initialize() {
        m_goingFastEnough = new Assertion(m_name, Level.ERROR, m_problemHandler) {

            protected void evaluate() throws AssertionFailureException {
                if(m_expectedMinVelocity > 0) {
                    assertTrue(m_input.pidGet() > m_expectedMinVelocity, "Not moving fast enough; expected " + m_expectedMinVelocity + " got " + m_input.pidGet());
                } else {
                    assertTrue(m_input.pidGet() < m_expectedMinVelocity, "Not moving fast enough; expected " + m_expectedMinVelocity + " got " + m_input.pidGet());
                }
            }
        };
        m_output.set(m_powerToApply);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        m_goingFastEnough.check();
        m_output.set(0);
    }

    protected void interrupted() {
        m_output.set(0);
    }
}
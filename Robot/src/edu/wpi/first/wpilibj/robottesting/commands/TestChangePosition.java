package edu.wpi.first.wpilibj.robottesting.commands;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.robottesting.Assertion;
import edu.wpi.first.wpilibj.robottesting.AssertionFailure;
import edu.wpi.first.wpilibj.robottesting.AssertionFailure.Level;
import edu.wpi.first.wpilibj.robottesting.ProblemHandler;
import edu.wpi.first.wpilibj.robottesting.ProblemManager;


/**
 * A Command which applies power to a motor and asserts that a sensor attached
 * to it moves at least a certain amount in the appropriate direction.
 * 
 * @author pmalmsten
 */
public class TestChangePosition extends Command {
    private String m_name;
    private SpeedController m_output;
    private PIDSource m_input;
    private double m_powerToApply;
    private double m_expectedMovement;
    private double m_lowerBound;
    private double m_upperBound;
    
    private boolean m_errorOccurred;
    private ProblemHandler m_problemHandler;
    private Assertion m_withinBounds;
    private Assertion m_movedFarEnough;
    private double m_startPosition;

    /**
     * Creates a new TestChangePosition command which applies the given power
     * setting to the given motor and asserts that the given position sensor
     * reflects an expected movement. At the end of the given duration, power
     * will be cut to the given output and the given position input will be
     * sampled to determine whether the mechanism moved the expected amount in
     * the appropriate direction.
     * 
     * @param name The name of this particular change position test.
     * @param system The subsystem this command should require when running.
     * @param output The output device to use.
     * @param input The input position sensor to use.
     * @param powerToApply The output setting to apply, between -1 and 1.
     * @param expectedMovement The expected movement to be observed from the
     *  given position sensor, in units of the input sensor's pidGet() method.
     * @param lowerBound The lower bound of the associated mechanism's position
     *  sensor. If this is exceeded, the test halts.
     * @param upperBound The upper bound of the associated mechanism's position
     *  sensor. If this is exceeded, the test halts.
     * @param duration The duration, in seconds, for which to apply the given
     *  power to the given output device. At the end of this period, the position
     *  of the mechanism will be sampled to determine whether the mechanism
     *  moved as expected.
     * @param problemHandler The handler to inform of the mechanism's failure to
     *  move as expected.
     */
    public TestChangePosition(String name,
                              Subsystem system,
                              SpeedController output,
                              PIDSource input,
                              double powerToApply,
                              double expectedMovement,
                              double lowerBound,
                              double upperBound,
                              double duration,
                              ProblemHandler problemHandler) {
        requires(system);

        m_name = name;
        m_output = output;
        m_input = input;
        m_powerToApply = powerToApply;
        m_expectedMovement = expectedMovement;
        m_lowerBound = lowerBound;
        m_upperBound = upperBound;
        m_problemHandler = problemHandler;

        setTimeout(duration);

        m_errorOccurred = false;
    }

    /**
     * Creates a new TestChangePosition command which applies the given power
     * setting to the given motor and asserts that the given position sensor
     * reflects an expected movement. At the end of the given duration, power
     * will be cut to the given output and the given position input will be
     * sampled to determine whether the mechanism moved the expected amount in
     * the appropriate direction.
     *
     * Failure of the mechanism to move as expected will be reported to the
     * singleton {@link ProblemManager} instance.
     *
     * @param name The name of this particular change position test.
     * @param system The subsystem this command should require when running.
     * @param output The output device to use.
     * @param input The input position sensor to use.
     * @param powerToApply The output setting to apply, between -1 and 1.
     * @param expectedMovement The expected movement to be observed from the
     *  given position sensor, in units of the input sensor's pidGet() method.
     * @param lowerBound The lower bound of the associated mechanism's position
     *  sensor. If this is exceeded, the test halts.
     * @param upperBound The upper bound of the associated mechanism's position
     *  sensor. If this is exceeded, the test halts.
     * @param duration The duration, in seconds, for which to apply the given
     *  power to the given output device. At the end of this period, the position
     *  of the mechanism will be sampled to determine whether the mechanism
     *  moved as expected.
     */
    public TestChangePosition(String name,
                              Subsystem system,
                              SpeedController output,
                              PIDSource input,
                              double powerToApply,
                              double expectedMovement,
                              double lowerBound,
                              double upperBound,
                              double duration) {
        this(name, system, output, input, powerToApply, expectedMovement,lowerBound,upperBound,duration, ProblemManager.getInstance());
    }

    protected void initialize() {
        m_startPosition = m_input.pidGet();
        ProblemHandler quitOnOutOfBounds = new ProblemHandler() {

            public void handleProblem(AssertionFailure af) {
                m_output.set(0);
                m_errorOccurred = true;
                m_problemHandler.handleProblem(af);
            }
        };

        m_withinBounds = new Assertion(m_name, Level.ERROR, quitOnOutOfBounds) {

            protected void evaluate() throws AssertionFailureException {
                double currentPosition = m_input.pidGet();

                assertTrue(currentPosition >= m_lowerBound && currentPosition <= m_upperBound, "Position out of bounds: " + currentPosition);
            }
        };

        m_movedFarEnough = new Assertion(m_name, Level.ERROR, m_problemHandler) {
           
            protected void evaluate() throws AssertionFailureException {
                double currentPositon = m_input.pidGet();
                
                if(m_expectedMovement > 0) {
                    assertTrue(currentPositon >= (m_startPosition + m_expectedMovement), "Moved too little; expected " + (m_startPosition + m_expectedMovement) + " got " + currentPositon);
                } else {
                    assertTrue(currentPositon <= (m_startPosition + m_expectedMovement), "Moved too little; expected " + (m_startPosition + m_expectedMovement) + " got " + currentPositon);
                }  
            }
        };

        m_output.set(m_powerToApply);
    }

    protected void execute() {
        m_withinBounds.check();
    }

    protected boolean isFinished() {
        return m_errorOccurred || isTimedOut();
    }

    protected void end() {
        m_movedFarEnough.check();
        m_output.set(0);
    }

    protected void interrupted() {
        m_output.set(0);
    }
}

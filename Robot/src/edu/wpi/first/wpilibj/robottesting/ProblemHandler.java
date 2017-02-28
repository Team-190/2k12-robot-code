package edu.wpi.first.wpilibj.robottesting;

/**
 * An object capable of responding to {@link AssertionFailure}s.
 *
 * @author pmalmsten
 */
public interface ProblemHandler {
    public void handleProblem(AssertionFailure af);
}
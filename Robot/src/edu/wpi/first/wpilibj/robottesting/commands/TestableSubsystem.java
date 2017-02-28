package edu.wpi.first.wpilibj.robottesting.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A subsystem that can test itself. Implementing classes are expected to
 * provide a group of commands which test it.
 * 
 * @author pmalmsten
 */
public interface TestableSubsystem {
    /**
     * Gets the test commands for this subsystem. This group must only be run
     * when the robot is in a safe configuration (i.e. off the ground, people
     * are not nearby, etc.).
     *
     * @return A CommandGroup which runs commands to test this mechanism.
     */
    public CommandGroup getTestCommands();
}

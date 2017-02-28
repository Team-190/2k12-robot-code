package edu.wpi.first.wpilibj.robottesting;

/**
 * Indicates that an assertion failed.
 * 
 * @author pmalmsten
 */
public class AssertionFailure {
    /**
     * Indicates the severity level of an assertion failure.
     */
    public static class Level {
        /**
         * AssertionFailures of this severity indicate that one or more
         * mechanisms may not operate properly.
         */
        public static Level ERROR = new Level();

        /**
         * AssertionFailures of this severity indicate that one or more mechanisms
         * may operate at reduced performance.
         */
        public static Level WARNING = new Level();
    }

    private final String m_testName;
    private final String m_failureDescription;
    private final Level m_level;

    /**
     * Creates a new AssertionFailure.
     *
     * @param testName The name of the test which failed.
     * @param failureDescrip The reason why the indicated test failed.
     * @param level The severity of the test failure.
     */
    public AssertionFailure(String testName, String failureDescrip, Level level) {
        m_testName = testName;
        m_failureDescription = failureDescrip;
        m_level = level;
    }

    /**
     * Returns the name of the test which failed.
     *
     * @return The name of the test which failed.
     */
    public String getName() {
        return m_testName;
    }

    /**
     * Returns the reason why a test failed.
     *
     * @return A string description of why a test failed.
     */
    public String getFailureDescription() {
        return m_failureDescription;
    }

    /**
     * Returns the severity of the test failure.
     *
     * @return An object representing the severity of the test failure.
     */
    public Level getLevel() {
        return m_level;
    }
}

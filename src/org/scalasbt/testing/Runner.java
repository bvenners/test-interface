package org.scalasbt.testing;

/**
 * Represents one run of a suite of tests.
 *
 * <p>
 * The run represented by a <code>Runner</code> has a lifecycle. The run begins when the
 * <code>Runner</code> is instantiated by the framework and returned to the client during
 * a <code>Framework.runner</code> invocation. The run continues until the client
 * invokes <code>done</code> on the <code>Runner</code>. Before invoking <code>done</code>,
 * the client can invoke the <code>task</code> methods as many times at it wants, but once
 * <code>done</code> has been invoked, the <code>Runner</code> enters "spent" mode. Any
 * subsequent invocations of <code>task</code> methods will be met with an
 * <code>IllegalStateException</code>.
 * </p>
 */
public interface Runner {

    /**
     * Returns a task that when executed will run the test class whose name is passed as
     * <code>testClassName</code>.
     *
     * <p>
     * The client can use this method to obtain tasks that will run test classes discovered
     * via a <code>Fingerprint</code>.
     * </p>
     *
     * @param testClassName the fully qualified name of the test class to be run by the returned task
     * @param fingerprint the fingerprint that was used to
     * @return a task that when executed will run the test class whose name was passed
     * @throws IllegalStateException if invoked after <code>done</code> has been invoked.
     */
    public Task task(String testClassName, Fingerprint fingerprint);
              // TODO: Do we need a isModule in the event Descriptor and maybe in the selectors?
    /**
     * Returns a task that when executed will run tests and suites determined by the
     * passed test class name and selectors.
     *
     * @param testClassName the fully qualified name of the test class to be run by the returned task
     * @param selectors a possibly empty array <code>Selectors</code> determining suites and tests to run
     * @return a task that when executed will run the selected test and/or suite "members" of the passed test class
     * @throws IllegalStateException if invoked after <code>done</code> has been invoked.
     */
    public Task task(String testClassName, Selector[] selectors);

    /**
     * Indicates the client is done with this <code>Runner</code> instance.
     *
     * <p>
     * After invoking the <code>done</code> method on a <code>Runner</code> instance, the client should no
     * longer invoke the <code>task</code> methods on that instance. (If the client does invoke <code>task</code>
     * after <code>done</code>, it will be rewarded with an <code>IllegalStateException</code>.
     * </p>
     *
     * <p>
     * Similarly, after returning from <code>done</code>, the test framework should no longer write
     * any messages to the <code>Logger</code>, nor fire any more events to the <code>EventHandler</code>,
     * passed to <code>Framework.runner</code>. If the test framework has not completed writing log messages
     * or firing events when the client invokes <code>done</code>, the framework should not return from
     * <code>done</code> until it is finished sending messages and events, and may block the thread
     * that invoked <code>done</code> until it is actually done.
     * </p>
     *
     * <p>
     * In short, by invoking <code>done</code>, the client indicates it is done invoking the <code>task</code>
     * methods for this run. By returning from <code>done</code>, the test framework indicates it is done writing
     * log messages and firing events for this run.
     * </p>
     *
     * <p>
     * If the client invokes <code>done</code> more than once on the same <code>Runner</code> instance, the test
     * framework should on subsequent invocations should throw <code>IllegalStateException</code>.
     * </p>
     *
     * <p>
     * The test framework may send a summary (i.e., a message giving total tests succeeded, failed, and
     * so on) to the user via a log message. If so, it should return
     * <code>true</code> from <code>done</code>. If not, it should return <code>false</code>. The client
     * may use the return value of <code>done</code> to decide whether to display its own summary message.
     * </p>
     *
     * @return true if the test framework sent a summary to the logger
     */
    public Boolean done();

    /* Nevermind.
     * Returns a test-framework specific summary string suitable for displaying to the user.
     *
     * <p>
     * If the passed <code>ansiCodesSupported</code> is false, the returned summary string should not
     * contain ANSI color commands. If true, the returned summary string may contain ANSI
     * color commands.
     * </p>
     *
     * <p>
     * The client can decide whether to actually show the summary string to the user.
     * For example, if only using one test framework, it user would probably prefer that the
     * client show the test-framework specific summary string, because it will look the same
     * as the summary they see when using the test framework directly (i.e., not through the
     * client). But if they are using multiple test frameworks, it may be better to show
     * a single, more general client-defined summary.
     * </p>
     *
     * <p>
     * The invocation of this method signals the end of the run. In addition to creating and
     * returning the summary string, the test framework should clean up any resources
     * associated with the run. The <code>Runner</code> is "spent" after <code>summarize</code> returns and
     * cannot be reused. Any subsequent invocations of a <code>task</code> method will be met with
     * <code>IllegalStateException</code>s.
     * </p>
     *
     * @param completionStatus indicates whether the run completed normally, or aborted or was
     *                         requested to stop (information which the test framework may use to
     *                         construct its summary string).
     * @param ansiCodesSupported indicates whether the summary string may contain ANSI color commands.
     *
     * @return a test-framework-specific string summarizing the test run
     */
    //public String summarize(RunStatus completionStatus, boolean ansiCodesSupported);
}

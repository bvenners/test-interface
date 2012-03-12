package org.scalasbt.testing;

/**
 * An event that indicates a test was cancelled because of some unmet precondition.
 *
 * <p>
 * A <em>canceled</em> test is one that was not able to run because of some unmet
 * precondition, such as a database not being present on the network. The test did
 * not fail or succeed, because it was not given a chance to run.
 * </p>
 */
public class SkippedEvent extends Event {
    public SkippedEvent(Descriptor descriptor) {
        super(descriptor);
    }
}
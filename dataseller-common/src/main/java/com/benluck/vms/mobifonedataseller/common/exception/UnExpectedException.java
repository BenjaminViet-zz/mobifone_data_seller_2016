package com.benluck.vms.mobifonedataseller.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Thi
 * Date: 4/15/13
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */

public class UnExpectedException extends RuntimeException {
    /**
     * Determines if a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = 474530560132875952L;

    /**
     * Constructor for DuplicateException.
     * @param message the detail message
     */
    public UnExpectedException(final String message) {
        super(message);
    }

    /**
     * Constructor for DuplicateException.
     * @param msg the detail message
     * @param cause the root cause (usually from using a underlying
     * data access API such as JDBC)
     */
    public UnExpectedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

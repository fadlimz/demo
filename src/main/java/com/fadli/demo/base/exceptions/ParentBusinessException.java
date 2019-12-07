package com.fadli.demo.base.exceptions;

public class ParentBusinessException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -4383240835635951087L;

    /**
     *
     */
    protected ParentBusinessException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    protected ParentBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    protected ParentBusinessException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    protected ParentBusinessException(Throwable cause) {
        super(cause);
    }
}

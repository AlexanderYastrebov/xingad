package com.xing.events.hr.position.software.engineer;

/**
 *
 * @author Alexander Yastrebov
 */
public class HRRuntimeException extends RuntimeException {

    public HRRuntimeException(String message) {
        super(message);
    }

    HRRuntimeException(Throwable cause) {
        super(cause);
    }
}

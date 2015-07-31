package com.xing.events.hr.service.exception;

/**
 *
 * @author Alexander Yastrebov
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

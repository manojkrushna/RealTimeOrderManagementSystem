package com.rtoms.user_management.user_management_service.util;

/**
 * @author Manoj Krushna Mohanta
 * 07-07-2025 00:47
 */
public class UserServiceException extends RuntimeException {

    private String errorCode;

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
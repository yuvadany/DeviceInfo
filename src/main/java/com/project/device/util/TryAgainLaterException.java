package com.project.device.util;

public class TryAgainLaterException extends RuntimeException {
    public TryAgainLaterException(String errorMessage) {
        super(MessageConstants.TRY_AGAIN+ errorMessage);
    }
}

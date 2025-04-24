package com.project.device.util;

public class TryAgainLaterException extends RuntimeException {
    public TryAgainLaterException(String errorMessage) {
        super("Something went wrong! PLease try again later " + errorMessage);
    }
}

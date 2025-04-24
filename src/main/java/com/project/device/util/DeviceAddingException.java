package com.project.device.util;

public class DeviceAddingException extends RuntimeException {
    public DeviceAddingException(String parameter) {
        super(MessageConstants.DEVICE_ADD_ERROR +parameter);
    }
}

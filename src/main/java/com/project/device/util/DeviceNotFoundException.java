package com.project.device.util;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String parameter) {
        super(MessageConstants.DEVICE_NOT_FOUND +parameter);
    }
}

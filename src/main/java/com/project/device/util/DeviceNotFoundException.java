package com.project.device.util;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String parameter) {
        super("No device found with " +parameter);
    }
}

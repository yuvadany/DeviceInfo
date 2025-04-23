package com.project.device.util;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String parameter) {
        super("Device not found for the " +parameter);
    }
}

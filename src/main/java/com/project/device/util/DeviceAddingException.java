package com.project.device.util;

public class DeviceAddingException extends RuntimeException {
    public DeviceAddingException(String parameter) {
        super("Device can not be added due to the error " +parameter);
    }
}

package com.project.device.util;

public class DeviceDeletionException extends RuntimeException {
    public DeviceDeletionException(String parameter) {
        super("Device can not be deleted since the device id " + parameter +" is in USE state " );
    }
}

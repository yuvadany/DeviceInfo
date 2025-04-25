package com.project.device.util;

public final class MessageConstants {
    private MessageConstants() {
    }

    public static final String DEVICE_NOT_FOUND = "No device found with ";
    public static final String DEVICE_ADD_ERROR = "Device can not be added due to the error ";
    public static final String TRY_AGAIN = "Something went wrong! PLease try again later ";
    public static final String DEVICE_IN_USE = "Device can not be updated when its in state IN_USE";
    public static final String IN_USE = "IN_USE";
    public static final String ID = "ID ";
    public static final String BRAND = "brand ";
    public static final String STATE = "state ";
    public static final String DEVICE_WITH_ID = "Device with ID  ";
    public static final String DELETED = " has been deleted!";
    public static final String UPDATED = " has been updated! ";
    public static final String CAN_NOT_DELETED =  " is in USE and can not be deleted.";
    public static final String SWAGGER_TITLE =  " REST APIs capable of persisting and managing device resources";
    public static final String HOME_CONTROLLER =  " Home Controller";
    public static final String ADD_API =  "Api to add a device";
    public static final String ADD_DESC =  "Adds a new device";
    public static final String FETCH_ALL_API =  "Api to fetch all Devices";
    public static final String FETCH_DEVICE_BY_STATE =  "Api to fetch one Device based on State";
    public static final String FETCH_DEVICE_BY_ID =  "Api to fetch one Device based on ID";
    public static final String FETCH_DEVICE_BY_BRAND =  "Api to fetch one Device based on Brand";
    public static final String UPDATE_API =  "Api to update a Device";
    public static final String DELETE_API =  "Api to delete a Device";
    public static final String FETCH_ONE_RANDOM_DEVICE =  "Api to fetch one Random Device";
    public static final String HOME_CONTROLLER_DESCRIPTION =  "Contains addDevice, fetchSingleRandomDevice, fetchDeviceById,fetchDeviceByBrand, fetchDeviceByState, updateDevice and deleteDevice Apis ";
}

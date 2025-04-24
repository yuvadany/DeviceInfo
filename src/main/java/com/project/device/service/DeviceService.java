package com.project.device.service;

import com.project.device.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DeviceService {
    public Device addDevice(Device device);

    public Device getAnyDevice();

    public List<Device> getDeviceByBrand(String brand);

    public List<Device> getAllDevicesInfo();

    public List<Device> getDeviceByState(String state);

    public Optional<Device> getSingleDevice(Long id);

    public Long deleteOneDevice(Long id);

    public Optional<Device> updateDevice(Long id, Device device);
}


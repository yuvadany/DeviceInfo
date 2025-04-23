package com.project.device.service;

import com.project.device.model.Device;
import com.project.device.serviceimpl.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceServiceImpl deviceServiceImpl;

    public Device addDevice(Device device)
    {
        return deviceServiceImpl.save(device);
    }
    public Device getAnyDevice()
    {
        return deviceServiceImpl.findAnyOne();
    }

    public List<Device> getDeviceByBrand(String brand)
    {
        return deviceServiceImpl.findByBrand(brand);
    }

    public List<Device> getAllDevicesInfo()
    {
        return deviceServiceImpl.findAll();
    }

    public List<Device> getDeviceByState(String state) {
        return deviceServiceImpl.findByState(state);
    }

    public Optional<Device> getSingleDevice(Long id) {
        return deviceServiceImpl.findById(id);
    }

    public  void deleteOneDevice(Long id) {
         deviceServiceImpl.deleteById(id);
    }
}


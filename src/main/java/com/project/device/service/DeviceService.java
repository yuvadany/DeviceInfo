package com.project.device.service;

import com.project.device.model.Device;
import com.project.device.repo.DeviceRepository;
import com.project.device.serviceimpl.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceServiceImpl serviceImpl;

    public List<Device> getAllDevicesInfo()
    {
        return serviceImpl.findAll();
    }
}


package com.project.device.serviceimpl;

import com.project.device.model.Device;
import com.project.device.repo.DeviceRepository;
import com.project.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Override
    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device getAnyDevice() {
        return deviceRepository.findAnyOneDevice();
    }

    @Override
    public List<Device> getDeviceByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    @Override
    public List<Device> getAllDevicesInfo() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> getDeviceByState(String state) {
        return deviceRepository.findByState(state);
    }

    @Override
    public Optional<Device> getSingleDevice(Long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public Long deleteOneDevice(Long id) {
        deviceRepository.deleteById(id);
        return id;
    }

    @Override
    public Optional<Device> updateDevice(Long id, Device updatedDevice) {
        return deviceRepository.findById(id)
                .map(existingDevice -> {
                    existingDevice.setBrand(updatedDevice.getBrand());
                    existingDevice.setName(updatedDevice.getName());
                    existingDevice.setState(updatedDevice.getState());
                    deviceRepository.save(existingDevice);
                    return existingDevice;
                });
    }

}
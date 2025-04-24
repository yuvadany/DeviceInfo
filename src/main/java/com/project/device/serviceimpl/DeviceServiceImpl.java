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
    public boolean deleteOneDevice(Long id, Device selectedDevice) {
        if (!"IN_USE".equalsIgnoreCase(String
                .valueOf(selectedDevice.getState()))) {
            deviceRepository.deleteById(id);
            return true;
        } else
            return false;

    }

    @Override
    public boolean updateDevice(Device existingDevice, Device newDeviceData) {
        if (!"IN_USE".equalsIgnoreCase(String
                .valueOf(existingDevice.getState())) && existingDevice.getState().equals(newDeviceData.getState()) && (
                !existingDevice.getBrand().equals(newDeviceData.getBrand()) || !existingDevice.getName().equals(newDeviceData.getName()))) {
            existingDevice.setBrand(newDeviceData.getBrand());
            existingDevice.setName(newDeviceData.getName());
            deviceRepository.save(existingDevice);
            return true;
        } else if (!existingDevice.getState().equals(newDeviceData.getState())) {
            existingDevice.setState(newDeviceData.getState());
            deviceRepository.save(existingDevice);
            return true;
        }
        return false;
    }

}
package com.project.device.service;

import com.project.device.dto.DeviceDTO;
import com.project.device.entity.DeviceEntity;
import com.project.device.repo.DeviceRepository;
import com.project.device.util.DeviceMapper;
import com.project.device.util.MessageConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;


    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Transactional
    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        return deviceMapper.toDTO(deviceRepository.save(deviceMapper.toEntity(deviceDTO) ));
    }

    @Transactional(readOnly = true)
    public DeviceDTO getAnyDevice() {
        return deviceMapper.toDTO(deviceRepository.findAnyOneDevice());
    }

    public List<DeviceDTO> getDeviceByFilter(String brand, String state, Long id) {
        return deviceRepository.findByFilter(brand, state, id).stream().map(deviceMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public Page<DeviceDTO> getAllDevicesInfo(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(deviceMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<DeviceDTO> getSingleDevice(Long id) {
        return deviceRepository.findById(id).map(deviceMapper::toDTO);
    }

    @Transactional
    public boolean deleteOneDevice(Long id, DeviceDTO selectedDeviceDTO) {
        if (!MessageConstants.IN_USE.equalsIgnoreCase(String
                .valueOf(selectedDeviceDTO.getState()))) {
            deviceRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    @Transactional
    public boolean updateDevice(DeviceDTO existingDeviceDTO, DeviceDTO newDeviceDTOData) {
        if (!MessageConstants.IN_USE.equalsIgnoreCase(String
                .valueOf(existingDeviceDTO.getState())) && existingDeviceDTO.getState().equals(newDeviceDTOData.getState()) && (
                !existingDeviceDTO.getBrand().equals(newDeviceDTOData.getBrand()) || !existingDeviceDTO.getName().equals(newDeviceDTOData.getName()))) {
            existingDeviceDTO.setBrand(newDeviceDTOData.getBrand());
            existingDeviceDTO.setName(newDeviceDTOData.getName());
            deviceRepository.save(deviceMapper.toEntity(existingDeviceDTO));
            return true;
        } else if (!existingDeviceDTO.getState().equals(newDeviceDTOData.getState())) {
            existingDeviceDTO.setState(newDeviceDTOData.getState());
            deviceRepository.save(deviceMapper.toEntity(existingDeviceDTO));
            return true;
        }
        return false;
    }

}
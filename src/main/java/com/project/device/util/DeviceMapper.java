package com.project.device.util;

import com.project.device.dto.DeviceDTO;
import com.project.device.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceDTO toDTO(DeviceEntity entity) {
        if (entity == null) {
            return null;
        }
        return new DeviceDTO(
                entity.getId(),
                entity.getName(),
                entity.getBrand(),
                DeviceDTO.State.valueOf(entity.getState().name()), // Enum-Mapping
                entity.getCreationTime()
        );
    }

    public static DeviceEntity toEntity(DeviceDTO dto) {
        if (dto == null) {
            return null;
        }
        DeviceEntity entity = new DeviceEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBrand(dto.getBrand());
        entity.setState(DeviceEntity.State.valueOf(dto.getState().name())); // Enum-Mapping
        // creationTime kommt von @PrePersist -> kein set nötig
        return entity;
    }
}

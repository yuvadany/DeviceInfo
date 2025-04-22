package com.project.device.repo;

import com.project.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DeviceRepository extends JpaRepository<Device, Long> {
}

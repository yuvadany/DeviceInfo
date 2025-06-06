package com.project.device.repo;

import com.project.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DeviceRepository extends JpaRepository<Device, Long> {
    @Query(value = "SELECT * FROM public.devices ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Device findAnyOneDevice() ;

    @Query(value = "SELECT * FROM public.devices WHERE LOWER(brand) = LOWER(:brand)", nativeQuery = true)
    List<Device> findByBrand(String brand);

    @Query(value = "delete FROM public.devices WHERE id = :id ", nativeQuery = true)
    Long deleteDeviceNotInUse(Long id);

    @Query(value ="SELECT * FROM public.devices WHERE LOWER(state) = LOWER(:state)", nativeQuery = true)
    List<Device> findByState(String state);

}

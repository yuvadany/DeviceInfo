package com.project.device.repo;

import com.project.device.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    @Query(value = "SELECT * FROM public.devices ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    DeviceEntity findAnyOneDevice() ;

    @Query(value = "SELECT * FROM public.devices WHERE id = :id or LOWER(brand) = LOWER(:brand) or LOWER(state) = LOWER(:state)", nativeQuery = true)
    List<DeviceEntity> findByFilter(String brand, String state, Long id);

    @Query(value = "delete FROM public.devices WHERE id = :id ", nativeQuery = true)
    Long deleteDeviceNotInUse(Long id);

    @Query(value ="SELECT * FROM public.devices WHERE LOWER(state) = LOWER(:state)", nativeQuery = true)
    List<DeviceEntity> findByState(String state);

}

package com.project.device.serviceimpl;

import com.project.device.model.Device;
import com.project.device.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DeviceServiceImpl implements DeviceRepository {

    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public void flush() {

    }

    @Override
    public <S extends Device> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Device> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Device> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Device getOne(Long aLong) {
        return null;
    }

    @Override
    public Device getById(Long aLong) {
        return null;
    }

    @Override
    public Device getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Device> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Device> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Device> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Device> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Device> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Device> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Device, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Device> S save(S entity) {
        return deviceRepository.save(entity);
    }

    @Override
    public <S extends Device> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Device> findById(Long aLong) {
        return deviceRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
       deviceRepository.deleteById(aLong);
    }

    @Override
    public void delete(Device entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Device> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Device> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Device> findAll(Pageable pageable) {
        return null;
    }

    @Query(value = "SELECT * FROM public.devices ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    public Device findAnyOne() {
        return deviceRepository.findAnyOne();
    }

    @Override
    public List<Device> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }


    @Override
    public List<Device> findByState(String state) {
        return deviceRepository.findByState(state);
    }

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

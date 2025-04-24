package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.service.DeviceService;
import com.project.device.util.DeviceAddingException;
import com.project.device.util.DeviceNotFoundException;
import com.project.device.util.MessageConstants;
import com.project.device.util.TryAgainLaterException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/devices")
public class HomeController {

    private final DeviceService deviceService;

    public HomeController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/addDevice")
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        try {
            Device savedDevice = deviceService.addDevice(device);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
        } catch (DataIntegrityViolationException e) {
            throw new DeviceAddingException(Objects.requireNonNull(e.getRootCause()).getMessage());
        } catch (Exception e) {
            throw new DeviceAddingException(HttpStatus.INTERNAL_SERVER_ERROR + " " + e.getMessage());
        }
    }

    @GetMapping("/fetchSingleDevice/{id}")
    public ResponseEntity<Device> fetchSingleDevice(@PathVariable Long id) {
        Device device = deviceService.getSingleDevice(id)
                .orElseThrow(() -> new DeviceNotFoundException(MessageConstants.ID + id));
        return ResponseEntity.ok(device);
    }

    @GetMapping("/fetchOneRandomDevice")
    public ResponseEntity<Device> fetchOneRandomDevice() {
        try {
            Device randomDevice = deviceService.getAnyDevice();
            return ResponseEntity.status(HttpStatus.OK).body(randomDevice);
        } catch (Exception e) {
            throw new TryAgainLaterException(e.getMessage());
        }

    }

    @GetMapping("/fetchByBrand")
    public ResponseEntity<List<Device>> fetchDeviceByBrand(@RequestParam String brand) {
        List<Device> device = deviceService.getDeviceByBrand(brand);
        if (!device.isEmpty())
            return ResponseEntity.ok(device);
        else
            throw new DeviceNotFoundException(MessageConstants.BRAND + brand);
    }

    @GetMapping("/fetchByState")
    public ResponseEntity<List<Device>> fetchDeviceByState(@RequestParam String state) {
        List<Device> device = deviceService.getDeviceByState(state);
        if (!device.isEmpty())
            return ResponseEntity.ok(device);
        else
            throw new DeviceNotFoundException(MessageConstants.STATE + state);

    }


    @GetMapping("/getAllDevices")
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevicesInfo();
        if (devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/updateDevice/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable Long id,
                                               @RequestBody Device newDeviceData) throws DeviceNotFoundException {
        var deviceOptional = deviceService.getSingleDevice(id);
        if (deviceOptional.isPresent()) {
            if (deviceService.updateDevice(deviceOptional.get(), newDeviceData)) {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID + id + MessageConstants.UPDATED);
            } else {
                return ResponseEntity.ok(MessageConstants.DEVICE_IN_USE);
            }
        } else
            throw new DeviceNotFoundException(MessageConstants.ID+ id);
    }

    @DeleteMapping("/deleteOneDevice/{id}")
    public ResponseEntity<String> deleteOneDeviceById(@PathVariable Long id) {
        var deviceOptional = deviceService.getSingleDevice(id);
        if (deviceOptional.isPresent()) {
            if (deviceService.deleteOneDevice(id, deviceOptional.get())) {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID + id + MessageConstants.DELETED);
            } else {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID+ id + MessageConstants.CAN_NOT_DELETED);
            }
        } else
            throw new DeviceNotFoundException(MessageConstants.ID + id);

    }

}

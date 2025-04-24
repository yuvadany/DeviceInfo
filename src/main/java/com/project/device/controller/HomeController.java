package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.service.DeviceService;
import com.project.device.util.DeviceAddingException;
import com.project.device.util.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/devices")
public class HomeController {

    @Autowired
    private DeviceService deviceService;

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
                .orElseThrow(() -> new DeviceNotFoundException("ID " + id));
        return ResponseEntity.ok(device);
    }

    @GetMapping("/fetchOneRandomDevice")
    public Device fetchOneRandomDevice() {
        return deviceService.getAnyDevice();

    }

    @GetMapping("/fetchByBrand")
    public ResponseEntity<List<Device>> fetchDeviceByBrand(@RequestParam String brand) {
        List<Device> device = deviceService.getDeviceByBrand(brand);
        if (!device.isEmpty())
            return ResponseEntity.ok(device);
        else
            throw new DeviceNotFoundException("brand " + brand);
    }

    @GetMapping("/fetchByState")
    public ResponseEntity<List<Device>> fetchDeviceByState(@RequestParam String state) {
        List<Device> device = deviceService.getDeviceByState(state);
        if (!device.isEmpty())
            return ResponseEntity.ok(device);
        else
            throw new DeviceNotFoundException("state " + state);

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
    public Device updateDevice(@PathVariable Long id,
                               @RequestBody Device updatedDevice) {
        deviceService.getSingleDevice(id)
                .orElseThrow(() -> new DeviceNotFoundException("ID " + id));
        return deviceService.updateDevice(id, updatedDevice).get();
    }

    @DeleteMapping("/deleteOneDevice/{id}")
    public ResponseEntity<String> deleteOneDeviceById(@PathVariable Long id) {
            deviceService.getSingleDevice(id)
                    .orElseThrow(() -> new DeviceNotFoundException("ID " + id));
            if (Objects.equals(deviceService.deleteOneDevice(id), id))
                return ResponseEntity.ok("Device with ID " + id + " has been deleted.");
       else return ResponseEntity.ok("Device with ID " + id + " is in USE and can not be deleted.");
    }

}

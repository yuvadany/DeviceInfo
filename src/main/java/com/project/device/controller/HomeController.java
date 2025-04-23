package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.model.ErrorResponse;
import com.project.device.service.DeviceService;
import com.project.device.util.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/devices")
public class HomeController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/welcome")
    public String welcome() {
        return " Welcome to Device INfo Application  ... " + LocalDateTime.now();
    }

    @PostMapping("/addDevice")
    public Device addDevice(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }


    @PutMapping("/updateDevice/{id}")
    public Device updateDevice(@PathVariable Long id,
                                         @RequestBody Device updatedDevice) {
        return deviceService.updateDevice(id,updatedDevice).get();
    }

    @GetMapping("/fetchSingleDevice/{id}")
    public ResponseEntity<Device> fetchSingleDevice(@PathVariable Long id) {
        Device device = deviceService.getSingleDevice(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
        return ResponseEntity.ok(device);
    }

    @GetMapping("/fetchOneRandomDevice")
    public Device fetchOneRandomDevice() {
        return deviceService.getAnyDevice();

    }

    @GetMapping("/fetchByBrand")
    public List<Device> fetchDeviceByBrand(@RequestParam  String brand) {
        return deviceService.getDeviceByBrand(brand);

    }

    @GetMapping("/fetchByState")
    public List<Device>  fetchDeviceByState(@RequestParam  String state) {
        return deviceService.getDeviceByState(state);

    }


    @GetMapping("/getAllDevices")
    public List<Device> getAllDevices() {
        return deviceService.getAllDevicesInfo();

    }

    @DeleteMapping("/deleteOneDevice/{id}")
    public void deleteOneDeviceById(@PathVariable Long id) {
         deviceService.deleteOneDevice(id);

    }

}

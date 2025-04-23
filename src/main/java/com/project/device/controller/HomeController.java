package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping("/fetchSingleDevice/{id}")
    public Optional<Device> fetcSingleDevice(@PathVariable Long id) {
        return deviceService.getSingleDevice(id);

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

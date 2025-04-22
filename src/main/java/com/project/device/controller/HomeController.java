package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/devices")
public class HomeController {

    @Autowired
    private DeviceService service;

    @GetMapping("/welcome")
    public String welcome() {
        return " Welcome to Device INfo Application  ... " + LocalDateTime.now();
    }

    @GetMapping("/getAllDevices")
    public List<Device> getAllDevices() {
        return service.getAllDevicesInfo();

    }

}

package com.project.device.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/devices")
public class HomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return " Welcome to Device INfo Application  ... "+ LocalDateTime.now();
    }
}

package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.repo.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

   @BeforeEach
    void setup() {
        deviceRepository.deleteAll();
    }

    @Test
    void shouldFetchSingleDevice() throws Exception {
        Device device = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);

        Device savedDevice = deviceRepository.save(device);

        mockMvc.perform(get("/v1/devices/fetchSingleDevice/" + savedDevice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDevice.getId()))
                .andExpect(jsonPath("$.name").value("Router"))
                .andExpect(jsonPath("$.brand").value("Cisco"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"))
                .andExpect(jsonPath("$.creationTime").exists());
    }

    @Test
    void shouldReturnNotFoundIfDeviceMissing() throws Exception {
        mockMvc.perform(get("/fetchSingleDevice/9999"))
                .andExpect(status().isNotFound());
    }
}

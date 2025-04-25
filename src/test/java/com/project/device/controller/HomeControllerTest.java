package com.project.device.controller;

import com.project.device.model.Device;
import com.project.device.repo.DeviceRepository;
import com.project.device.util.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @BeforeEach
    void setup() {
        deviceRepository.deleteAll();
    }


    @Test
    void shouldFetchSingleDevice() throws Exception {
        Device device = new Device(null, "Router", "CISCO", Device.State.AVAILABLE, LocalDateTime.now(), null);

        Device savedDevice = deviceRepository.save(device);

        mockMvc.perform(get("/v1/devices/fetchSingleDevice/" + savedDevice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDevice.getId()))
                .andExpect(jsonPath("$.name").value("Router"))
                .andExpect(jsonPath("$.brand").value("CISCO"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"))
                .andExpect(jsonPath("$.creationTime").exists());
    }

    @Test
    void shouldReturnNotFoundIfDeviceMissing() throws Exception {
        mockMvc.perform(get("/v1/devices/fetchSingleDevice/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFetchDevicesByBrand() throws Exception {
        Device deviceRouter = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceSwitch = new Device(null, "Switch", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);

        deviceRepository.save(deviceRouter);
        deviceRepository.save(deviceSwitch);

        mockMvc.perform(get("/v1/devices/fetchByBrand?brand=Cisco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Router"))
                .andExpect(jsonPath("$[1].name").value("Switch"))
                .andExpect(jsonPath("$[0].brand").value("Cisco"))
                .andExpect(jsonPath("$[1].brand").value("Cisco"));
    }

    @Test
    void shouldReturnNotFoundIfNoDeviceFoundByBrand() throws Exception {
        mockMvc.perform(get("/v1/devices/fetchByBrand?brand=NonExistentBrand"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldFetchDevicesByStateWhenStateExists() throws Exception {
        // Prepare test data
        Device deviceRouter = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceSwitch = new Device(null, "Switch", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceServer = new Device(null, "Server", "Dell", Device.State.IN_USE, LocalDateTime.now(), null);

        deviceRepository.save(deviceRouter);
        deviceRepository.save(deviceSwitch);
        deviceRepository.save(deviceServer);
        mockMvc.perform(get("/v1/devices/fetchByState?state=AVAILABLE"))
                .andExpect(status().isOk()) // Expecting status 200 OK
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Router"))
                .andExpect(jsonPath("$[1].name").value("Switch"))
                .andExpect(jsonPath("$[0].state").value("AVAILABLE"))
                .andExpect(jsonPath("$[1].state").value("AVAILABLE"));
    }

    @Test
    void shouldReturnNotFoundIfNoDeviceFoundForState() throws Exception {
        mockMvc.perform(get("/v1/devices/fetchByState?state=NON_EXISTENT_STATE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllDevicesWhenDevicesExist() throws Exception {
        Device deviceRouter = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceSwitch = new Device(null, "Switch", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceModem = new Device(null, "Modem", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);

        deviceRepository.save(deviceRouter);
        deviceRepository.save(deviceSwitch);
        deviceRepository.save(deviceModem);

        mockMvc.perform(get("/v1/devices/getAllDevices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Router"))
                .andExpect(jsonPath("$[1].name").value("Switch"))
                .andExpect(jsonPath("$[2].name").value("Modem"));
    }

    @Test
    void shouldReturnNoContentWhenNoDevicesExist() throws Exception {
        mockMvc.perform(get("/v1/devices/getAllDevices"))
                .andExpect(status().isNoContent());
    }


    @Test
    void shouldUpdateDeviceSuccessfully() throws Exception {
        Device device = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device savedDevice = deviceRepository.save(device);

        Device updatedDeviceData = new Device(savedDevice.getId(), "Updated Router", "Cisco", Device.State.INACTIVE, LocalDateTime.now(), 1);

        mockMvc.perform(put("/v1/devices/updateDevice/" + savedDevice.getId())
                        .contentType("application/json")
                        .content("{\"id\":" + savedDevice.getId() + ",\"name\":\"Updated Router\",\"brand\":\"Cisco\",\"state\":\"INACTIVE\",\"creationTime\":\"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Device with ID  " + savedDevice.getId() + " has been updated!"));
    }

    @Test
    void shouldReturnConflictIfDeviceInUse() throws Exception {
        Device device = new Device(null, "Router", "Cisco", Device.State.IN_USE, LocalDateTime.now(), null);
        Device savedDevice = deviceRepository.save(device);

        mockMvc.perform(put("/v1/devices/updateDevice/" + savedDevice.getId())
                        .contentType("application/json")
                        .content("{\"id\":" + savedDevice.getId() + ",\"name\":\"Updated Router\",\"brand\":\"Cisco\",\"state\":\"IN_USE\",\"creationTime\":\"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(MessageConstants.DEVICE_IN_USE));
    }

    @Test
    void shouldReturnNotFoundIfDeviceDoesNotExistForUpdate() throws Exception {
        mockMvc.perform(put("/v1/devices/updateDevice/9999")
                        .contentType("application/json")
                        .content("{\"id\":9999,\"name\":\"Nonexistent Device\",\"brand\":\"XYZ\",\"state\":\"AVAILABLE\",\"creationTime\":\"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFetchOneRandomDeviceSuccessfully() throws Exception {
        Device deviceRouter = new Device(null, "Router", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceSwitch = new Device(null, "Switch", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);
        Device deviceModem = new Device(null, "Modem", "Cisco", Device.State.AVAILABLE, LocalDateTime.now(), null);

        deviceRepository.save(deviceRouter);
        deviceRepository.save(deviceSwitch);
        deviceRepository.save(deviceModem);

        mockMvc.perform(get("/v1/devices/fetchOneRandomDevice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.creationTime").exists());
    }

  
}

package com.project.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.device.dto.DeviceDTO;
import com.project.device.entity.DeviceEntity;
import com.project.device.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean   // ✅ Replaces @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddDevice_success() throws Exception {
        DeviceDTO dto = new DeviceDTO(
                1L,
                "Router",                      // name
                "Samsung",                     // brand
                DeviceDTO.State.AVAILABLE,     // state (enum!)
                LocalDateTime.now()            // creationTime
        );
        when(deviceService.addDevice(any(DeviceDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/v1/devices/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.state").value("active"));
    }

    @Test
    void testFetchSingleDevice_found() throws Exception {
        DeviceDTO dto = new DeviceDTO(
                1L,
                "Router",                      // name
                "Samsung",                     // brand
                DeviceDTO.State.INACTIVE,     // state (enum!)
                LocalDateTime.now()            // creationTime
        );
        when(deviceService.getSingleDevice(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/v1/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Apple"));
    }

    @Test
    void testFetchSingleDevice_notFound() throws Exception {
        when(deviceService.getSingleDevice(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/devices/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllDevices_withContent() throws Exception {
        DeviceDTO dto = new DeviceDTO(
                1L,
                "Router",                      // name
                "Samsung",                     // brand
                DeviceDTO.State.AVAILABLE,     // state (enum!)
                LocalDateTime.now()            // creationTime
        );
        Page<DeviceDTO> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);
        when(deviceService.getAllDevicesInfo(any())).thenReturn(page);

        mockMvc.perform(get("/v1/devices/all?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Sony"));
    }

    @Test
    void testGetAllDevices_empty() throws Exception {
        Page<DeviceDTO> emptyPage = Page.empty();
        when(deviceService.getAllDevicesInfo(any())).thenReturn(emptyPage);

        mockMvc.perform(get("/v1/devices/all"))
                .andExpect(status().isNoContent());
    }
}

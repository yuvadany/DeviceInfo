package com.project.device.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class DeviceDTO {

    @Schema(hidden = true)
    private Long id;

    @Schema(description = "", example = "Modem")
    private String name;

    @Schema(description = "", example = "ABC")
    private String brand;

    @Schema(description = "", example = "AVAILABLE")
    private State state;

    @Schema(description = "", example = "2025-04-25T13:03:41.550Z")
    private LocalDateTime creationTime;

    public enum State {
        AVAILABLE,
        IN_USE,
        INACTIVE
    }

    public DeviceDTO() {}

    public DeviceDTO(Long id, String name, String brand, State state, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}

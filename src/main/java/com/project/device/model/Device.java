package com.project.device.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
public class Device {
    public Device() {

    }

    public Device(Long id, String name, String brand, State state, LocalDateTime creationTime, Integer version) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;
    @Schema(description = "", example = "Modem")
    private String name;
    @Schema(description = "", example = "ABC")
    private String brand;

    @Enumerated(EnumType.STRING)
    @Schema(description = "", example = "AVAILABLE")
    private State state;

    @Column(name = "creation_time", updatable = false)
    @Schema(description = "", example = "2025-04-25T13:03:41.550Z")
    private LocalDateTime creationTime;

    //added for the error org.springframework.orm.ObjectOptimisticLockingFailureException: Row was updated or deleted by another transaction
    @Version
    private Integer version;


    @PrePersist
    protected void onCreate() {
        creationTime = LocalDateTime.now();
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

    public enum State {
        AVAILABLE,
        IN_USE,
        INACTIVE
    }
}
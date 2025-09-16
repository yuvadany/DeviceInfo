package com.project.device.controller;

import com.project.device.dto.DeviceDTO;
import com.project.device.service.DeviceService;
import com.project.device.util.DeviceAddingException;
import com.project.device.util.DeviceNotFoundException;
import com.project.device.util.MessageConstants;
import com.project.device.util.TryAgainLaterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/devices")
@Tag(name = MessageConstants.HOME_CONTROLLER, description = MessageConstants.HOME_CONTROLLER_DESCRIPTION)
public class HomeController {

    private final DeviceService deviceService;

    public HomeController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    @Operation(
            summary = MessageConstants.ADD_API,
            description = MessageConstants.ADD_DESC,
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_CREATED, description = "", content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_ACCEPTABLE, description = MessageConstants.DEVICE_ADD_ERROR,
                            content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR, description = MessageConstants.INTERNAL_SERVER_ERROR,
                            content = @Content())
            }

    )
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO DeviceDTO) {
        try {
            DeviceDTO savedDeviceDTO = deviceService.addDevice(DeviceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDeviceDTO);
        } catch (DataIntegrityViolationException e) {
            throw new DeviceAddingException(Objects.requireNonNull(e.getRootCause()).getMessage());
        } catch (Exception e) {
            throw new DeviceAddingException(HttpStatus.INTERNAL_SERVER_ERROR + " " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = MessageConstants.FETCH_DEVICE_BY_ID,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = "", content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_FOUND, description = MessageConstants.DEVICE_ADD_ERROR,
                            content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR, description = MessageConstants.INTERNAL_SERVER_ERROR,
                            content = @Content())
            }
    )
    public ResponseEntity<DeviceDTO> fetchSingleDevice(@PathVariable Long id) {
        DeviceDTO DeviceDTO = deviceService.getSingleDevice(id)
                .orElseThrow(() -> new DeviceNotFoundException(MessageConstants.ID + id));
        return ResponseEntity.ok(DeviceDTO);
    }

    @GetMapping("/randomDevice")
    @Operation(summary = MessageConstants.FETCH_ONE_RANDOM_DEVICE,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = "", content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_FOUND, description = MessageConstants.TRY_AGAIN,
                            content = @Content())
            }
    )
    public ResponseEntity<DeviceDTO> fetchOneRandomDevice() {
        try {
            DeviceDTO randomDeviceDTO = deviceService.getAnyDevice();
            return ResponseEntity.status(HttpStatus.OK).body(randomDeviceDTO);
        } catch (Exception e) {
            throw new TryAgainLaterException(e.getMessage());
        }

    }

    @GetMapping("")
    @Operation(summary = MessageConstants.FETCH_DEVICE_BY_BRAND_STATE_ID,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = "", content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_FOUND, description = MessageConstants.DEVICE_NOT_FOUND + MessageConstants.BRAND,
                            content = @Content())
            }
    )
    public ResponseEntity<List<DeviceDTO>> fetchDeviceByBrand(@RequestParam(required = false) Long id, @RequestParam(required = false) String brand, @RequestParam(required = false) String state) {
        List<DeviceDTO> DeviceDTO = deviceService.getDeviceByFilter(brand, state, id);
        if (!DeviceDTO.isEmpty())
            return ResponseEntity.ok(DeviceDTO);
        else
            throw new DeviceNotFoundException(MessageConstants.BRAND + brand);
    }

    @GetMapping("/all")
    @Operation(summary = MessageConstants.FETCH_ALL_API,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = "", content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NO_CONTENT, description = MessageConstants.DEVICE_NOT_FOUND + MessageConstants.STATE,
                            content = @Content())
            }
    )
    public ResponseEntity<Page<DeviceDTO>> getAllDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DeviceDTO> deviceEntities = deviceService.getAllDevicesInfo(pageable);
        if (deviceEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(deviceEntities);
    }

    @PutMapping("/{id}")
    @Operation(summary = MessageConstants.UPDATE_API,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = MessageConstants.DEVICE_WITH_ID + MessageConstants.UPDATED, content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = MessageConstants.DEVICE_IN_USE,
                            content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_FOUND, description = MessageConstants.DEVICE_NOT_FOUND + MessageConstants.ID,
                            content = @Content())
            }
    )
    public ResponseEntity<String> updateDevice(@PathVariable Long id,
                                               @RequestBody DeviceDTO newDeviceDTOData) throws DeviceNotFoundException {
        var deviceOptional = deviceService.getSingleDevice(id);
        if (deviceOptional.isPresent()) {
            if (deviceService.updateDevice(deviceOptional.get(), newDeviceDTOData)) {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID + id + MessageConstants.UPDATED);
            } else {
                return ResponseEntity.ok(MessageConstants.DEVICE_IN_USE);
            }
        } else
            throw new DeviceNotFoundException(MessageConstants.ID + id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = MessageConstants.DELETE_API,
            description = "",
            responses = {
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = MessageConstants.DEVICE_WITH_ID + MessageConstants.DELETED, content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_OK, description = MessageConstants.DEVICE_WITH_ID + MessageConstants.CAN_NOT_DELETED,
                            content = @Content()),
                    @ApiResponse(responseCode = MessageConstants.HTTP_STATUS_NOT_FOUND, description = MessageConstants.DEVICE_NOT_FOUND + MessageConstants.ID,
                            content = @Content())
            })
    public ResponseEntity<String> deleteOneDeviceById(@PathVariable Long id) {
        var deviceOptional = deviceService.getSingleDevice(id);
        if (deviceOptional.isPresent()) {
            if (deviceService.deleteOneDevice(id, deviceOptional.get())) {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID + id + MessageConstants.DELETED);
            } else {
                return ResponseEntity.ok(MessageConstants.DEVICE_WITH_ID + id + MessageConstants.CAN_NOT_DELETED);
            }
        } else
            throw new DeviceNotFoundException(MessageConstants.ID + id);

    }

}

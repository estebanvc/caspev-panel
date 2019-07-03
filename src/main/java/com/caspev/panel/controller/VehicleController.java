package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.service.VehicleService;
import com.caspev.panel.service.dto.VehicleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Web controller for managing Vehicle.
 */
@Controller
public class VehicleController {

    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public String listVehicles(Model model) {
        log.debug("GET request to get all Vehicles");
        List<VehicleDTO> vehicleDTOList = vehicleService.findAll();
        model.addAttribute("vehicleDTOList", vehicleDTOList);
        return "vehicle/list";
    }

    @GetMapping("/vehicles/add")
    public String createVehicle(@ModelAttribute VehicleDTO vehicleDTO, Model model) {
        model.addAttribute("formAction", "/vehicles/add");
        return "/vehicle/form";
    }

    @PostMapping("/vehicles/add")
    public String createVehicle(@Valid @ModelAttribute VehicleDTO vehicleDTO,
                                BindingResult bindingResult, Model model) {
        log.debug("POST request to save Vehicle : {}", vehicleDTO);
        model.addAttribute("formAction", "/vehicles/add");
        if (!bindingResult.hasErrors()) {
            if (vehicleService.findOneByPlate(vehicleDTO.getPlate()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "vehicleDTO",
                                "plate",
                                "El vehículo ya existe"
                        )
                );
            } else {
                vehicleDTO.setPlate(vehicleDTO.getPlate().toUpperCase());
                vehicleService.save(vehicleDTO);
                return "redirect:/vehicles";
            }
        }

        return "vehicle/form";
    }

    @GetMapping("/vehicles/{uuid}")
    public String getVehicle(@PathVariable String uuid, Model model) {
        log.debug("GET request to get Vehicle : {}", uuid);
        model.addAttribute("formAction", "/vehicles/" + uuid);
        VehicleDTO vehicleDTO = vehicleService.findOneByUuid(uuid)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("vehicleDTO", vehicleDTO);

        return "vehicle/form";
    }

    @PostMapping("/vehicles/{uuid}")
    public String updateVehicle(@PathVariable String uuid,
                                @Valid @ModelAttribute VehicleDTO vehicleDTO,
                                BindingResult bindingResult, Model model) {
        log.debug("POST request to update Vehicle : {}", vehicleDTO);
        model.addAttribute("formAction", "/vehicles/" + uuid);

        if (!bindingResult.hasErrors()) {
            Long vehicleId = vehicleService.findOneByUuid(uuid)
                    .orElseThrow(ResourceNotFoundException::new).getId();
            vehicleDTO.setId(vehicleId);
            vehicleService.save(vehicleDTO);
            model.addAttribute("recordUpdated", true);
        }

        return "vehicle/form";
    }

    @GetMapping("/vehicles/{uuid}/delete")
    public String deleteVehicle(@PathVariable String uuid) {
        log.debug("GET request to delete Vehicle : {}", uuid);
        vehicleService.findOneByUuid(uuid)
                .ifPresent(vehicleDTO -> vehicleService.delete(vehicleDTO.getUuid()));
        return "redirect:/vehicles";
    }
}
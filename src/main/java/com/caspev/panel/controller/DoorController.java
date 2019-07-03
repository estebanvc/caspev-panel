package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.service.DoorService;
import com.caspev.panel.service.dto.DoorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Web controller for managing Door.
 */
@Controller
public class DoorController {

    private final Logger log = LoggerFactory.getLogger(DoorController.class);

    private final DoorService doorService;

    public DoorController(DoorService doorService) {
        this.doorService = doorService;
    }

    @GetMapping("/doors")
    public String listDoors(Model model) {
        log.debug("GET request to get all Doors");
        List<DoorDTO> doorDTOList = doorService.findAll();
        model.addAttribute("doorDTOList", doorDTOList);
        return "door/list";
    }

    @GetMapping("/doors/add")
    public String createDoor(@ModelAttribute DoorDTO doorDTO, Model model) {
        model.addAttribute("formAction", "/doors/add");
        return "/door/form";
    }

    @PostMapping("/doors/add")
    public String createDoor(@Valid @ModelAttribute DoorDTO doorDTO,
                             BindingResult bindingResult, Model model) {
        log.debug("POST request to save Door : {}", doorDTO);
        model.addAttribute("formAction", "/doors/add");

        if (!bindingResult.hasErrors()) {
            doorService.save(doorDTO);
            return "redirect:/doors";
        }

        return "door/form";
    }

    @GetMapping("/doors/{uuid}")
    public String getDoor(@PathVariable String uuid, Model model) {
        log.debug("GET request to get Door : {}", uuid);
        model.addAttribute("formAction", "/doors/" + uuid);
        DoorDTO doorDTO = doorService.findOneByUuid(uuid)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("doorDTO", doorDTO);

        return "door/form";
    }

    @PostMapping("/doors/{uuid}")
    public String updateDoor(@PathVariable String uuid,
                             @Valid @ModelAttribute DoorDTO doorDTO,
                             BindingResult bindingResult, Model model) {
        log.debug("POST request to update Door : {}", doorDTO);
        model.addAttribute("formAction", "/doors/" + uuid);

        if (!bindingResult.hasErrors()) {
            Long doorId = doorService.findOneByUuid(uuid)
                    .orElseThrow(ResourceNotFoundException::new).getId();
            doorDTO.setId(doorId);
            doorService.save(doorDTO);
            model.addAttribute("recordUpdated", true);
        }

        return "door/form";
    }

    @GetMapping("/doors/{uuid}/delete")
    public String deleteDoor(@PathVariable String uuid) {
        log.debug("GET request to delete Door : {}", uuid);
        doorService.findOneByUuid(uuid)
                .ifPresent(doorDTO -> doorService.delete(doorDTO.getUuid()));
        return "redirect:/doors";
    }
}
package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.service.NfcCardService;
import com.caspev.panel.service.dto.NfcCardDTO;
import com.caspev.panel.service.mapper.NfcCardMapper;
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
 * Web controller for managing NfcCard.
 */
@Controller
public class NfcCardController {

    private final Logger log = LoggerFactory.getLogger(NfcCardController.class);

    private final NfcCardService nfcCardService;

    public NfcCardController(NfcCardService nfcCardService) {
        this.nfcCardService = nfcCardService;
    }

    @GetMapping("/nfc-cards")
    public String listNfcCards(Model model) {
        log.debug("GET request to get all NfcCards");
        List<NfcCardDTO> nfcCardDTOList = nfcCardService.findAll();
        model.addAttribute("nfcCardDTOList", nfcCardDTOList);
        return "nfc-card/list";
    }

    @GetMapping("/nfc-cards/add")
    public String createNfcCard(@ModelAttribute NfcCardDTO nfcCardDTO, Model model) {
        model.addAttribute("formAction", "/nfc-cards/add");
        return "nfc-card/form";
    }

    @PostMapping("/nfc-cards/add")
    public String createNfcCard(@Valid @ModelAttribute NfcCardDTO nfcCardDTO,
                                BindingResult bindingResult, Model model) {
        log.debug("POST request to save NfcCard : {}", nfcCardDTO);
        model.addAttribute("formAction", "/nfc-cards/add");
        if (!bindingResult.hasErrors()) {
            if (nfcCardService.findOneByCode(nfcCardDTO.getCode()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "nfcCardModel",
                                "code",
                                "El código ya existe"
                        )
                );
            } else {
                nfcCardService.save(nfcCardDTO);
                return "redirect:/nfc-cards";
            }
        }

        return "nfc-card/form";
    }

    @GetMapping("/nfc-cards/{uuid}")
    public String getNfcCard(@PathVariable String uuid, Model model) {
        log.debug("GET request to get NfcCard : {}", uuid);
        model.addAttribute("formAction", "/nfc-cards/" + uuid);
        NfcCardDTO nfcCardDTO = nfcCardService.findOneByUuid(uuid)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("nfcCardDTO", nfcCardDTO);

        return "nfc-card/form";
    }

    @PostMapping("/nfc-cards/{uuid}")
    public String updateNfcCard(@PathVariable String uuid,
                                @Valid @ModelAttribute NfcCardDTO nfcCardDTO,
                                BindingResult bindingResult, Model model) {
        log.debug("POST request to update NfcCard : {}", nfcCardDTO);
        model.addAttribute("formAction", "/nfc-cards/" + uuid);

        if (!bindingResult.hasErrors()) {
            Long nfcCardId = nfcCardService.findOneByUuid(uuid)
                    .orElseThrow(ResourceNotFoundException::new).getId();
            nfcCardDTO.setId(nfcCardId);
            nfcCardService.save(nfcCardDTO);
            model.addAttribute("recordUpdated", true);
        }

        return "nfc-card/form";
    }

    @GetMapping("/nfc-cards/{uuid}/delete")
    public String deleteNfcCard(@PathVariable String uuid) {
        log.debug("GET request to delete NfcCard : {}", uuid);
        nfcCardService.findOneByUuid(uuid)
                .ifPresent(nfcCardDTO -> nfcCardService.delete(nfcCardDTO.getUuid()));
        return "redirect:/nfc-cards";
    }
}
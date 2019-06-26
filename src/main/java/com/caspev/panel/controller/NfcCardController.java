package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.domain.NfcCard;
import com.caspev.panel.model.NfcCardModel;
import com.caspev.panel.repository.NfcCardRepository;
import org.springframework.data.domain.Sort;
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

@Controller
public class NfcCardController {

    private final NfcCardRepository nfcCardRepository;

    public NfcCardController(NfcCardRepository nfcCardRepository) {
        this.nfcCardRepository = nfcCardRepository;
    }

    @GetMapping("/nfc-cards")
    public String listNfcCards(Model model) {

        List<NfcCard> nfcCardList = nfcCardRepository.findAll(Sort.by("id").descending());
        model.addAttribute("nfcCardList", nfcCardList);

        return "nfc-card/list";
    }

    @GetMapping("/nfc-cards/add")
    public String addNfcCard(@ModelAttribute NfcCardModel nfcCardModel, Model model) {
        model.addAttribute("formAction", "/nfc-cards/add");
        return "nfc-card/form";
    }

    @PostMapping("/nfc-cards/add")
    public String addNfcCard(@Valid @ModelAttribute NfcCardModel nfcCardModel,
                             BindingResult bindingResult, Model model) {

        model.addAttribute("formAction", "/nfc-cards/add");

        if (!bindingResult.hasErrors()) {
            NfcCard nfcCard = new NfcCard();
            nfcCard.setCode(nfcCardModel.getCode());
            if (nfcCardRepository.findByCode(nfcCardModel.getCode()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "nfcCardModel",
                                "code",
                                "El código ya existe"
                        )
                );
            } else {
                nfcCardRepository.save(nfcCard);
                return "redirect:/nfc-cards";
            }
        }

        return "nfc-card/form";
    }

    @GetMapping("/nfc-cards/{uuid}")
    public String showNfcCard(@PathVariable String uuid, Model model) {

        model.addAttribute("formAction", "/nfc-cards/" + uuid);
        NfcCard nfcCard = nfcCardRepository.findByUuid(uuid).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("nfcCardModel", nfcCard);

        return "nfc-card/form";
    }

    @PostMapping("/nfc-cards/{uuid}")
    public String updateNfcCard(@PathVariable String uuid,
                                @Valid @ModelAttribute NfcCardModel nfcCardModel,
                                BindingResult bindingResult, Model model) {

        model.addAttribute("formAction", "/nfc-cards/" + uuid);

        if (!bindingResult.hasErrors()) {
            NfcCard nfcCard = nfcCardRepository.findByUuid(uuid).orElseThrow(ResourceNotFoundException::new);
            nfcCard.setCode(nfcCardModel.getCode());
            nfcCardRepository.save(nfcCard);
            model.addAttribute("recordUpdated", true);
        }

        return "nfc-card/form";
    }

    @GetMapping("/nfc-cards/{uuid}/delete")
    public String deleteNfcCard(@PathVariable String uuid) {
        nfcCardRepository.findByUuid(uuid).ifPresent(nfcCardRepository::delete);
        return "redirect:/nfc-cards";
    }

}
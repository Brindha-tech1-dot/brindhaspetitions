package com.example.brindhaspetitions.controller;

import com.example.brindhaspetitions.model.Petition;
import com.example.brindhaspetitions.model.Signature;
import com.example.brindhaspetitions.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PetitionController {

    @Autowired
    private PetitionService service;

    // Root mapping -> show all petitions
    @GetMapping("/")
    public String listPetitions(Model model) {
        model.addAttribute("petitions", service.getAllPetitions());
        return "petition"; // petition.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create";
    }

    @PostMapping("/create")
    public String createPetition(@ModelAttribute Petition petition) {
        service.addPetition(petition);
        return "redirect:/";
    }

    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable Long id, Model model) {
        Petition petition = service.getPetitionById(id).orElse(null);
        if (petition == null) return "redirect:/";
        model.addAttribute("petition", petition);
        model.addAttribute("signature", new Signature());
        return "view";
    }

    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable Long id, @ModelAttribute Signature signature) {
        service.addSignature(id, signature);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchResults(@RequestParam String keyword, Model model) {
        List<Petition> results = service.searchPetitions(keyword);
        model.addAttribute("results", results);
        return "results";
    }
}
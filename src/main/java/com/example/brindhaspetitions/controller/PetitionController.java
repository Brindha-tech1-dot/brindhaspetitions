package com.example.brindhaspetitions.controller;

import com.example.brindhaspetitions.model.Petition;
import com.example.brindhaspetitions.model.Signature;
import com.example.brindhaspetitions.service.PetitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetitionController {

    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    // Redirect root to home
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/home";
    }

    // Home page listing all petitions
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "home"; // your template listing all petitions
    }

    // Show create petition form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create"; // matches create.html
    }

    // Handle create petition form submission
    @PostMapping("/create")
    public String createPetition(@ModelAttribute Petition petition) {
        petitionService.addPetition(petition);
        return "redirect:/home";
    }

    // Show search form
    @GetMapping("/search")
    public String searchForm() {
        return "search"; // matches search.html
    }

    // Handle search results
    @PostMapping("/search")
    public String searchResults(@RequestParam String keyword, Model model) {
        model.addAttribute("petitions", petitionService.searchPetitions(keyword));
        return "results"; // matches results.html
    }

    // View a petition by ID
    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable Long id, Model model) {
        petitionService.getPetitionById(id).ifPresent(p -> model.addAttribute("petition", p));
        return "view"; // matches view.html
    }

    // Sign a petition
    @PostMapping("/petition/{id}/sign")
    public String signPetition(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email
    ) {
        petitionService.signPetition(id, name, email);
        return "redirect:/petition/" + id;
    }
}
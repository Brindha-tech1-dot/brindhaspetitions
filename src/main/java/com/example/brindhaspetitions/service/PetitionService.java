package com.example.brindhaspetitions.service;

import com.example.brindhaspetitions.model.Petition;
import com.example.brindhaspetitions.model.Signature;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetitionService {

    private List<Petition> petitions = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Initialize with some sample data (IDs as Long)
        petitions.add(new Petition(1L, "Save the Park", "Protect the community park"));
        petitions.add(new Petition(2L, "Improve Campus Wi-Fi", "Upgrade university Wi-Fi speeds"));
    }

    // Get all petitions
    public List<Petition> getAllPetitions() {
        return petitions;
    }

    // Add a new petition
    public void addPetition(Petition petition) {
        // Auto-generate ID
        Long newId = petitions.stream()
                .mapToLong(Petition::getId)
                .max()
                .orElse(0L) + 1;
        petition.setId(newId);
        petitions.add(petition);
    }

    // Get petition by ID
    public Optional<Petition> getPetitionById(Long id) {
        return petitions.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Search petitions by keyword (title only)
    public List<Petition> searchPetitions(String keyword) {
        return petitions.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Sign a petition
    public void signPetition(Long id, Signature signature) {
        getPetitionById(id).ifPresent(p -> p.addSignature(signature));
    }
}

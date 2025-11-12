package com.example.brindhaspetitions.service;

import com.example.brindhaspetitions.model.Petition;
import com.example.brindhaspetitions.model.Signature;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetitionService {
    private List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        // Preload university petitions
        petitions.add(new Petition(1L, "Free Parking on Campus", "Students request free parking and more parking spaces."));
        petitions.add(new Petition(2L, "Free University Lunch", "Students request free lunch on campus."));
        petitions.add(new Petition(3L, "Free Stationery for Students", "Students request free stationery for all courses."));
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public Optional<Petition> getPetitionById(Long id) {
        return petitions.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void addPetition(Petition petition) {
        petition.setId((long) (petitions.size() + 1));
        petitions.add(petition);
    }

    public void addSignature(Long petitionId, Signature signature) {
        getPetitionById(petitionId).ifPresent(p -> p.addSignature(signature));
    }

    public List<Petition> searchPetitions(String keyword) {
        List<Petition> results = new ArrayList<>();
        for (Petition p : petitions) {
            if (p.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    p.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
}
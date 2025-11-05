package com.example.brindhaspetitions.model;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private Long id; // changed from String to Long
    private String title;
    private String description;
    private List<Signature> signatures = new ArrayList<>();

    // Default constructor
    public Petition() {}

    // Constructor with fields
    public Petition(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Signature> getSignatures() { return signatures; }

    public void addSignature(Signature signature) {
        this.signatures.add(signature);
    }
}

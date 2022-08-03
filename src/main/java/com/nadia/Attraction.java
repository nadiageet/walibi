package com.nadia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attraction implements Requirement{
    private String name;

    private List<Requirement> requirements = new ArrayList<>();

    private User user;

    public Attraction() {
    }

    public Attraction(String name) {
        this.name = name;
     }

    public String getName() {
        return name;
    }

    public Attraction setName(String name) {
        this.name = name;
        return this;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public Attraction setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
        return this;
    }

    public List<Requirement> addRequirement(Requirement requirement){
         requirements.add(requirement);
        return requirements;
    }

    @Override
    public boolean test(User user) {
        return false;
    }

    @Override
    public boolean verifyAccess(User user) {
        return user.getAge(LocalDate.now()) > 12;
    }
}

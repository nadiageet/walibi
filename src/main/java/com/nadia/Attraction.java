package com.nadia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attraction {
    private String name;

    private List<Requirement> requirements = new ArrayList<>();

    public Attraction() {
    }
    public Attraction(Requirement requirement) {
        requirements.add(requirement);
    }

    public Attraction(String name, List<Requirement> requirements) {
        this.name = name;
        this.requirements = requirements;
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

    public List<Requirement> addRequirement(Requirement requirement){
         requirements.add(requirement);
        return requirements;
    }


    public boolean verifyAccess(User user) {
        for (Requirement requirement: requirements){
            if (!requirement.test(user)){
                return false;
            }
        }

        return true;
    }

    public List<Requirement> createAttraction(int age){
        return this.addRequirement(user -> user.getAge(LocalDate.now()) <= age);

    }
}

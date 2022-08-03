package com.nadia;

import java.time.LocalDate;
import java.util.*;

public class Attraction {
    private String name;

    private List<Requirement> requirements = new ArrayList<>();

    private Set<User> players = new HashSet<>();

    public Attraction() {
    }

    public Attraction(Requirement requirement) {
        requirements.add(requirement);
    }

    public Attraction(String name, Requirement requirement) {
        this.name = name;
        requirements.add(requirement);
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

    public List<Requirement> addRequirement(Requirement requirement) {
        requirements.add(requirement);
        return requirements;
    }


    public boolean verifyAccess(User user) {
        for (Requirement requirement : requirements) {
            if (!requirement.test(user)) {
                return false;
            }
        }

        return true;
    }

    public List<Requirement> createAttraction(int age) {
        return this.addRequirement(user -> user.getAge(LocalDate.now()) <= age);

    }

    public String play(User user) {

        if (!verifyAccess(user)) {
            throw new IllegalStateException("le joueur n'a pas le droit de jouer");
        }
        players.add(user);
        return "joueur " + user.getName() + " joue sur l'attraction " + this.name;
    }

    public int getPlayers() {
        return players.size();
    }
}

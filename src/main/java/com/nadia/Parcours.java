package com.nadia;

import java.util.ArrayList;
import java.util.List;

public class Parcours {

    public static final int MAX_NUMBER_OF_ATTRACTIONS = 3;
    private final User user;

    private final List<Attraction> attractions = new ArrayList<>();


    public Parcours(User user) {

        this.user = user;
    }

    public void addAttraction(Attraction attraction) {
        if (attractions.size() < MAX_NUMBER_OF_ATTRACTIONS && attraction.verifyAccess(user)) {
            attractions.add(attraction);
        }
    }

    public Attraction getAttractionByIndex(int n) {
        if(attractions.size() < n + 1){
            return null;
        } else {
            return attractions.get(n);
        }
    }

    public Attraction getFirstAttraction() {
        if (attractions.isEmpty()) {
            return null;
        } else {
            return attractions.get(0);
        }
    }

    public Attraction getSecondAttraction() {
        if (attractions.size() < 2) {
            return null;
        } else {
            return attractions.get(1);
        }
    }

    public Attraction getThirdAttraction() {
        if (attractions.size() < 3) {
            return null;
        } else {
            return attractions.get(2);
        }
    }

    public int getNumberOfAttractions() {
        return attractions.size();
    }

}

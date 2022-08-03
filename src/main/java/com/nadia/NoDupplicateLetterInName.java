package com.nadia;

import java.util.HashSet;
import java.util.Set;

public class NoDupplicateLetterInName implements Requirement{
    @Override
    public boolean test(User user) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < user.getName().length(); i++) {
            char letter = Character.toLowerCase(user.getName().charAt(i));
            boolean added = set.add(letter);
            if(!added){
                return false;
            }
        }
        return true;
    }
}

package com.nadia;

import java.util.Arrays;
import java.util.List;

public class FinishWithVowel implements Requirement {

    public static final List<Character> VOYELLES = Arrays.asList('a', 'e', 'i', 'o', 'u', 'y');

    @Override
    public boolean test(User user) {
        String name = user.getName();
        char lastLetter = name.charAt(name.length() - 1);
        return VOYELLES.contains(lastLetter);

    }
}

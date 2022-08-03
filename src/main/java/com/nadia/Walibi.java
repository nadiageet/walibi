package com.nadia;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.nadia.Gender.F;

public class Walibi {

    private final EnumMap<AttractionName, Attraction> attractions = new EnumMap<>(AttractionName.class);

    public Walibi() {
        attractions.put(AttractionName.TOM_AND_JERRY, new Attraction());
        Attraction vowelGame = new Attraction();
        vowelGame.addRequirement(user1 -> user1.getGender().equals(F));
        vowelGame.addRequirement(new FinishWithVowel());
        attractions.put(AttractionName.VOWEL_GAME, vowelGame);
        Attraction noDuplicateGame = new Attraction(new NoDupplicateLetterInName());
        attractions.put(AttractionName.NO_DUPLICATE_GAME, noDuplicateGame);
        attractions.put(AttractionName.TARZAN, new Attraction());
        attractions.put(AttractionName.ZORRO, new Attraction());
        attractions.put(AttractionName.TINTIN, new Attraction(minAge(3, LocalDate.now())));
        attractions.put(AttractionName.TITANIC, new Attraction(minAge(12, LocalDate.now())));
        attractions.put(AttractionName.ALIEN, new Attraction(minAge(18, LocalDate.now())));
        attractions.put(AttractionName.BIRTHDAY_PARTY, new Attraction(isBirthDay()));
    }

    private static Requirement isBirthDay() {
        return user -> {
            int day = user.getBirthDate().getDayOfMonth();
            int month = user.getBirthDate().getMonthValue();
            LocalDate today = LocalDate.now();
            return day == today.getDayOfMonth() && month == today.getMonthValue();
        };
    }

    public Attraction getAttraction(AttractionName name) {
        return attractions.get(name);
    }

    public static Requirement minAge(int age, LocalDate now) {
        return user1 -> user1.getAge(now) >= age;
    }

    public List<Attraction> getAllAttractions(){
        return new ArrayList<>(attractions.values());
    }


}

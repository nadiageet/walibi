package com.nadia;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.nadia.Gender.F;

public class Walibi {

    private final EnumMap<AttractionName, Attraction> attractions = new EnumMap<>(AttractionName.class);

    public Walibi() {
        attractions.put(AttractionName.TOM_AND_JERRY, new Attraction("TOM_AND_JERRY"));
        Attraction vowelGame = new Attraction("VOWEL_GAME");
        vowelGame.addRequirement(user1 -> user1.getGender().equals(F));
        vowelGame.addRequirement(new FinishWithVowel());
        attractions.put(AttractionName.VOWEL_GAME, vowelGame);
        Attraction noDuplicateGame = new Attraction(new NoDupplicateLetterInName());
        noDuplicateGame.setName("NO_DUPLICATE_GAME");
        attractions.put(AttractionName.NO_DUPLICATE_GAME, noDuplicateGame);
        attractions.put(AttractionName.TARZAN, new Attraction("TARZAN"));
        attractions.put(AttractionName.ZORRO, new Attraction("ZORRO"));
        attractions.put(AttractionName.TINTIN, new Attraction("TINTIN", minAge(3, LocalDate.now())));
        attractions.put(AttractionName.TITANIC, new Attraction("TITANIC", minAge(12, LocalDate.now())));
        attractions.put(AttractionName.ALIEN, new Attraction("ALIEN", minAge(18, LocalDate.now())));
        attractions.put(AttractionName.BIRTHDAY_PARTY, new Attraction("BDAY", isBirthDay()));
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


    public Parcours generateRandomParcours(User user) {

        List<Attraction> attractionList = getAllAttractions().stream()
                .filter(attraction -> attraction.verifyAccess(user))
                .collect(Collectors.toList());
        Collections.shuffle(attractionList);
        Parcours p = new Parcours(user);
        for (Attraction attraction:attractionList){
            p.addAttraction(attraction);
        }


        return p;
    }

}

package com.nadia;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static com.nadia.Gender.F;
import static com.nadia.Gender.M;

class AttractionTest {
    List<User> users = new ArrayList<>();
    private Walibi walibi;


    @BeforeEach
    void setUp() {

        users.add(UserFactory.female(1, "nadia", Arrays.asList("admin"), LocalDate.parse("1981-05-24")));
        users.add(UserFactory.female(2, "sofia", Arrays.asList("manager"), LocalDate.parse("2013-09-23")));
        users.add(new UserBuilder().id(3).name("guillaumE").roles(Arrays.asList("commerciale")).birthDate(LocalDate.parse("1993-09-07")).gender(M).createUser());
        users.add(UserFactory.female(4, "lyna", Arrays.asList("dev"), LocalDate.parse("2021-07-04")));
        users.add(UserFactory.female(5, "zohra", Arrays.asList("lead"), LocalDate.parse("1954-01-01")));
        users.add(UserFactory.female(6, "aicha", Arrays.asList("dev"), LocalDate.parse("1983-05-24")));
        users.add(UserFactory.female(7, "wahiba", Arrays.asList("admin"), LocalDate.parse("1979-05-24")));
        users.add(new UserBuilder().id(8).name("yacine").roles(Arrays.asList("dev")).birthDate(LocalDate.parse("1991-05-24")).gender(M).createUser());
        users.add(UserFactory.female(9, "lydia", Arrays.asList("admin"), LocalDate.parse("2013-05-24")));
        users.add(UserFactory.female(10, "amandine", Arrays.asList("lead"), LocalDate.parse("2021-05-24")));

        walibi = new Walibi();
    }

    @Test
    public void addAttraction() {

        Attraction piscine = new Attraction("piscine enfant");
        piscine.addRequirement(user -> user.getAge(LocalDate.now()) < 12);


        for (User user1 : users) {
            if (piscine.verifyAccess(user1)) {
                System.out.println(user1.getName() + " peut faire l'attraction");
            } else {
                System.out.println(user1.getName() + " ne peut pas faire l'attraction");

            }
//            if(user1.getAge(LocalDate.now()) < 12){
//                piscine.addRequirement(requirement.test(user1));
//            }
        }

    }

    @Test
    public void noRequirement() {
        User user = UserFactory.simpleUser();
        Attraction a = walibi.getAttraction(AttractionName.TOM_AND_JERRY);
        Assertions.assertThat(a.verifyAccess(user)).isTrue();
    }

    @Test
    void sensationForte() {
        User user = UserFactory.simpleUser();
        Attraction a = walibi.getAttraction(AttractionName.ALIEN);
        Assertions.assertThat(a.verifyAccess(user)).isTrue();
    }

    @Test
    void MinTwelveYears() {
        User user = new UserBuilder().birthDate(LocalDate.now().minusYears(11)).createUser();
        Attraction a = walibi.getAttraction(AttractionName.TITANIC);
        Assertions.assertThat(a.verifyAccess(user)).isFalse();
    }

    @Test
    void MinTreeYears() {
        User user = new UserBuilder().birthDate(LocalDate.now().minusYears(2)).createUser();
        Attraction a = walibi.getAttraction(AttractionName.TINTIN);
        Assertions.assertThat(a.verifyAccess(user)).isFalse();
    }

    @Test
    void FemaleAndFinishBy() {

        User user = new UserBuilder().name("lyna")
                .gender(F).createUser();
        Attraction a = walibi.getAttraction(AttractionName.VOWEL_GAME);
        Assertions.assertThat(a.verifyAccess(user)).isTrue();

    }

    @Test
    void dontContainARepeatedLetter() {
        User nadia = new UserBuilder().name("Nadia").createUser();
        User ana = new UserBuilder().name("Ana").createUser();
        User lyna = new UserBuilder().name("Lyna").createUser();

        Attraction a = walibi.getAttraction(AttractionName.NO_DUPLICATE_GAME);

        Assertions.assertThat(a.verifyAccess(nadia)).isFalse();
        Assertions.assertThat(a.verifyAccess(ana)).isFalse();
        Assertions.assertThat(a.verifyAccess(lyna)).isTrue();

    }

    @Test
    void isBirthDay() {
        LocalDate now = LocalDate.now();

        User nadia = new UserBuilder().birthDate(now.minusYears(18).minusDays(15)).createUser();
        User soria = new UserBuilder().birthDate(now.minusYears(18)).createUser();

        Attraction a = walibi.getAttraction(AttractionName.BIRTHDAY_PARTY);

        Assertions.assertThat(a.verifyAccess(nadia)).isFalse();
        Assertions.assertThat(a.verifyAccess(soria)).isTrue();
    }

    @Test
    void numberOfAttraction() {
        Map<User, Integer> numberOfAttraction = new HashMap<>();
        for (User user : users) {
            Integer number = 0;
            for (Attraction attraction : walibi.getAllAttractions()) {
                if (attraction.verifyAccess(user)) {
                    number++;
                }
            }
            numberOfAttraction.put(user, number);

        }
        numberOfAttraction.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
                        .forEach(entry ->
                System.out.println(entry.getKey().getName() + " nombre d attraction: " + entry.getValue()));
    }


}

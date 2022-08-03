package com.nadia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nadia.Gender.M;

class AttractionTest {

    Attraction piscine = new Attraction("piscine enfant");
    List<User> users = new ArrayList<>();
    private User user;


    @BeforeEach
    void setUp(){

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
    }

    @Test
    public void addAttraction(){
        for (User user1: users){
            if (piscine.verifyAccess(user1)){
                piscine.addRequirement(new Requirement());
            }
        }
    }


}

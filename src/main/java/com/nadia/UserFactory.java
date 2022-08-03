package com.nadia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public static User female(int id, String name, List<String> roles, LocalDate birthDate){
        User user = new UserBuilder().id(id).name(name).roles(roles).birthDate(birthDate).gender(Gender.F).createUser();
        user.learnToSing();
        return user;
    }

    public static User simpleUser() {
        return new UserBuilder().id(1).name("name").roles(new ArrayList<>()).birthDate(LocalDate.parse("2000-01-01")).gender(Gender.F).createUser();
    }
}

package com.nadia;

import java.time.LocalDate;
import java.util.List;

public class UserFactory {

    public static User female(int id, String name, List<String> roles, LocalDate birthDate){
        User user = new UserBuilder().id(id).name(name).roles(roles).birthDate(birthDate).gender(Gender.F).createUser();
        user.learnToSing();
        return user;
    }
}

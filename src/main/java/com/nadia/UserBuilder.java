package com.nadia;

import java.time.LocalDate;
import java.util.List;

public class UserBuilder {
    private Integer id;
    private String name;
    private List<String> roles;
    private LocalDate birthDate;
    private Gender gender;

    private Integer beauty ;

    public UserBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder roles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public User createUser() {
        return new User(id, name, roles, birthDate, gender);
    }
}
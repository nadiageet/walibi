package com.nadia;

import java.time.LocalDate;
import java.util.List;

public class User implements SingerAbility {

    private Integer id;
    private String name;
    private List<String> roles;

    private LocalDate birthDate;

    private Gender gender;

    private SingerAbility singerAbility;

    private Integer beauty;


    public User(Integer id, String name, List<String> roles, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.birthDate = birthDate;
        this.gender = gender;
        singerAbility = new BadSinger();
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }

    public int getAge(LocalDate now) {
        return getBirthDate().until(now).getYears();
    }

    @Override
    public String sing() {
        return singerAbility.sing();
    }

    public User setBeauty(int grade) {
        if (!(1 <= grade && grade <= 10)) {
            throw new IllegalArgumentException();
        }
        this.beauty = grade;
        return this;
    }

    public String dance() {
        return null;
    }

    public String shout() {
        return null;
    }

    public void forgetToSing() {
        singerAbility = new BadSinger();
    }

    public void learnToSing() {
        this.singerAbility = new GoodSinger();
    }

    public User id(Integer id) {
        this.id = id;
        return this;
    }

    public User name(String name) {
        this.name = name;
        return this;
    }

    public User roles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public User birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public User gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public User singerAbility(SingerAbility singerAbility) {
        this.singerAbility = singerAbility;
        return this;
    }

    public Integer getBeauty() {
        return beauty;
    }

    public boolean isBeautiful(){
        return beauty >= 8;
    }
}

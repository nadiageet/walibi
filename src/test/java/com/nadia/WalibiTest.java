package com.nadia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.WriteAbortedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WalibiTest {


    private Walibi walibi;

    @BeforeEach
    void setUp() {
        walibi = new Walibi();
    }

    @Test
    void shouldGenerateRandomParcours() {
        User user = new UserBuilder()
                .name("Guigui")
                .gender(Gender.M)
                .birthDate(LocalDate.now().minusYears(20))
                .createUser();

        Parcours parcours = walibi.generateRandomParcours(user);

        for (int i = 0; i < parcours.getNumberOfAttractions(); i++) {
            Attraction a = parcours.getAttractionByIndex(i);
            a.play(user);
            a.play(user);
        }
    }
}
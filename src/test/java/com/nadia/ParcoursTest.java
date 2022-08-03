package com.nadia;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParcoursTest {

    @Test
    void test() {
        User nadia = new UserBuilder().name("Nadia").birthDate(LocalDate.now().minusYears(41))
                .createUser();
        Parcours parcours = new Parcours(nadia);

        Attraction aquatique = new Attraction(Walibi.minAge(12, LocalDate.now()));

        parcours.addAttraction(aquatique);
        Attraction a2 = new Attraction();
        parcours.addAttraction(a2);
        Attraction a3 = new Attraction();
        parcours.addAttraction(a3);

        Attraction attraction = parcours.getAttractionByIndex(0);
        Attraction ea2 = parcours.getAttractionByIndex(1);
        Attraction ea3 = parcours.getAttractionByIndex(2);

        assertThat(aquatique).isSameAs(attraction);
        assertThat(a2).isSameAs(ea2);
        assertThat(a3).isSameAs(ea3);
    }

    @Test
    void shouldOnlyAddToParcoursIfAcceptedByRequirements() {
        User nadia = new UserBuilder().name("Nadia")
                .gender(Gender.F)
                .birthDate(LocalDate.now().minusYears(41))
                .createUser();
        Parcours parcours = new Parcours(nadia);

        Attraction a = new Attraction(user -> user.getGender() == Gender.M);

        parcours.addAttraction(a);

        assertThat(parcours.getFirstAttraction()).isNull();
    }

    @Test
    void numberOfAttractions() {
        User nadia = new UserBuilder().name("Nadia")
                .gender(Gender.F)
                .birthDate(LocalDate.now().minusYears(41))
                .createUser();
        Parcours parcours = new Parcours(nadia);
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());

        assertThat(parcours.getNumberOfAttractions()).isEqualTo(3);
    }
    @Test
    void maxNumberOfAttractionIs3() {
        User nadia = new UserBuilder().name("Nadia")
                .gender(Gender.F)
                .birthDate(LocalDate.now().minusYears(41))
                .createUser();
        Parcours parcours = new Parcours(nadia);
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());
        parcours.addAttraction(new Attraction());

        assertThat(parcours.getNumberOfAttractions()).isEqualTo(3);
    }
}
package movies.service;

import movies.Customer;
import movies.Movie;
import movies.Rental;
import movies.Type;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RentalServiceTest {

    Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
    Movie actionMovie = new Movie("Bad Boy", Type.ACTION);
    Movie adventureMovie = new Movie("aventura", Type.ADVENTURE);
    private RentalService rentalService = new RentalService();

    @Test
    public void rental() {
        Rental rent = rentalService.rent(customer, actionMovie);

        assertThat(rent.getPrice())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void aventureMovieIs8() {

        Rental rent = rentalService.rent(customer, adventureMovie);

        assertThat(rent.getPrice())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("8"));
    }

    @Test
    void rentalActionMovieAgeLower16Years() {
        Customer customer = new Customer("nadia", LocalDate.now().minusYears(15));
        Movie movieAction = new Movie("movie Action", Type.ACTION);
        Throwable throwable = catchThrowable(() -> rentalService.rent(customer, movieAction));

        assertThat(throwable).isNotNull();


    }

    @Test
    void rentalAdventureMovieAgeLower16Years() {
        Customer customer = new Customer("sofia", LocalDate.now().minusYears(11));
        Movie movie = new Movie("movie aventure", Type.ADVENTURE);
        Throwable throwable = catchThrowable(() -> rentalService.rent(customer, movie));

        assertThat(throwable).isNotNull();
    }

    @Test
    void addPoint() {
        Customer customer = new Customer("nadia", LocalDate.now().minusYears(18));
        Rental rentAdventure = rentalService.rent(customer, adventureMovie);
        Rental rentAction = rentalService.rent(customer, actionMovie);
        assertThat(customer.getPoints())
                .isEqualTo(18);

    }

    @Test
    void expirationDate() {

    }
}
package movies.service;

import movies.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RentalServiceTest {

    Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
    Movie actionMovie = new Movie("Bad Boy", Type.ACTION);
    Movie adventureMovie = new Movie("aventura", Type.ADVENTURE);
    Movie comedieMovie = new Movie("comedies", Type.COMEDY);
    Movie fantesieMovie = new Movie("fantesie", Type.FANTASY);
    private RentalService rentalService = new RentalService(new RentalRepository());

    @Test
    public void rental() {
        Rental rent = rentalService.rent(customer, actionMovie, LocalDateTime.now());

        assertThat(rent.getPrice())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void aventureMovieIs8() {

        Rental rent = rentalService.rent(customer, adventureMovie, LocalDateTime.now());

        assertThat(rent.getPrice())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("8"));
    }

    @Test
    void rentalActionMovieAgeLower16Years() {
        Customer customer = new Customer("nadia", LocalDate.now().minusYears(15));
        Movie movieAction = new Movie("movie Action", Type.ACTION);
        Throwable throwable = catchThrowable(() -> rentalService.rent(customer, movieAction, LocalDateTime.now()));

        assertThat(throwable).isNotNull();


    }

    @Test
    void rentalAdventureMovieAgeLower16Years() {
        Customer customer = new Customer("sofia", LocalDate.now().minusYears(11));
        Movie movie = new Movie("movie aventure", Type.ADVENTURE);
        Throwable throwable = catchThrowable(() -> rentalService.rent(customer, movie, LocalDateTime.now()));

        assertThat(throwable).isNotNull();
    }

    @Test
    void addPointOnRent() {
        Customer customer = new Customer("nadia", LocalDate.now().minusYears(18));
        Rental rentAdventure = rentalService.rent(customer, adventureMovie, LocalDateTime.now());
        assertThat(customer.getPoints())
                .isEqualTo(8);
    }

    @Test
    void addBonusPoint() {
        Customer customer = new Customer("nadia", LocalDate.now().minusYears(18));
        Rental rentAdventure = rentalService.rent(customer, adventureMovie, LocalDateTime.now());
        Rental rentAction = rentalService.rent(customer, actionMovie, LocalDateTime.now());
        assertThat(customer.getPoints())
                .isEqualTo(8 + 10 * 2);
    }

    @Test
    void expirationDate() {

        Customer customer = new Customer("sofia", LocalDate.parse("2013-09-23"));
        Rental rental = rentalService.rent(customer, comedieMovie, LocalDateTime.now());
        rental.setRentedAt(LocalDateTime.now().minusDays(1));
        assertThat(rental.isActive(LocalDateTime.now())).isTrue();
    }

     @Test
    void isPrenium(){
         Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
         customer.addPoint(30);
         assertThat(customer.isPrenium()).isTrue();
     }

     @Test
    void rentFANTASYMovie(){
         Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
         Rental rental = rentalService.rent(customer, fantesieMovie, LocalDateTime.now());
         assertThat(rental.getPrice()).isEqualTo("12");
     }

    @Test
    void expirationDateVIPCustumer() {

        Customer customer = new Customer("sofia", LocalDate.parse("2013-09-23"));
        Rental rental = rentalService.rent(customer, comedieMovie, LocalDateTime.now());
        customer.addPoint(40);
        rental.setRentedAt(LocalDateTime.now().minusHours(50));
        assertThat(rental.isActive(LocalDateTime.now())).isTrue();
    }

    @Test
    void avtivesCustomer(){
        Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
        Rental rental = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        Rental rental1 = rentalService.rent(customer, actionMovie, LocalDateTime.now().minusHours(1000));
        Rental rental2 = rentalService.rent(customer, comedieMovie, LocalDateTime.now().minusHours(1550));
        Rental rental3 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));

        String printActiveRent = rentalService.printActiveRents(customer, LocalDateTime.now());

        assertThat(printActiveRent).isEqualTo("AVENTURA prix : 8€, 62 hours left");
    }

    @Test
    void noActivesCustomer(){
        Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));

        String printActiveRent = rentalService.printActiveRents(customer, LocalDateTime.now());

        assertThat(printActiveRent).isEqualTo("Aucune location en cours");
    }

    @Test
    void infosVIPCustomer(){
        Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
        Rental rental = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        Rental rental1 = rentalService.rent(customer, actionMovie, LocalDateTime.now().minusHours(1000));
        Rental rental2 = rentalService.rent(customer, comedieMovie, LocalDateTime.now().minusHours(1550));
        Rental rental3 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));
        String infos =  customer.infos(customer);
        assertThat(infos).isEqualTo("nadia a un badge VIP et a 4 locations.");
    }

    @Test
    void infosCustomer(){
        Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
        Rental rental = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        String infos =  customer.infos(customer);
        assertThat(infos).isEqualTo("nadia a 1 locations.");
    }

    @Test
    public void rentalFrequencies(){
        Customer customer = new Customer("nadia", LocalDate.parse("1981-05-24"));
        Rental rental = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        Rental rental1 = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        Rental rental2 = rentalService.rent(customer, fantesieMovie, LocalDateTime.now().minusHours(50));
        Rental rental3 = rentalService.rent(customer, actionMovie, LocalDateTime.now().minusHours(1000));
        Rental rental4 = rentalService.rent(customer, comedieMovie, LocalDateTime.now().minusHours(1550));
        Rental rental5 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));
        Rental rental6 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));
        Rental rental7 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));
        Rental rental8 = rentalService.rent(customer, adventureMovie, LocalDateTime.now().minusHours(10));
        String rentalFrequencies =  rentalService.rentalFrequencies();
        assertThat(rentalFrequencies).isEqualTo("[ACTION: 1, ADVENTURE: 4, COMEDY: 1, FANTASY: 3]");


    }
}
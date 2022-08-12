package movies.service;

import movies.Customer;
import movies.Movie;
import movies.Rental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RentalService {

    public Rental rent(Customer customer, Movie movie) {
        LocalDateTime rentalDate = LocalDateTime.now();
        movie.getType().minimumAge().ifPresent(age -> ensureMinimumAge(customer, age));
        BigDecimal price = movie.getType().price();
        int points = computePoints(price, customer, rentalDate);
        customer.addPoint(points);

        Rental rental = new Rental(movie, rentalDate, customer, price);
        customer.getLocations().add(rental);
        return rental;
    }

    private static int computePoints(BigDecimal price, Customer customer, LocalDateTime rentalDate) {
        int points = price.intValue();
        int nomberrOfactiveRental = (int) customer.getLocations().stream()
                .filter(rental -> rental.isActive(rentalDate))
                .count();
        if (nomberrOfactiveRental > 0){
            points *= 2;
        }
        return points;
    }

    private void ensureMinimumAge(Customer customer, Integer minimumAge) {
        if (customer.getAge(LocalDate.now()) < minimumAge) {
            throw new RuntimeException("le film ne peut être loué; trop jeune");
        }
    }


}

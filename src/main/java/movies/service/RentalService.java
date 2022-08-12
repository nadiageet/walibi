package movies.service;

import movies.Customer;
import movies.Movie;
import movies.Rental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class RentalService {

    public Rental rent(Customer customer, Movie movie) {
        LocalDateTime date = LocalDateTime.now();
        movie.getType().minimumAge().ifPresent(age -> ensureMinimumAge(customer, age));
        BigDecimal price = movie.getType().price();
        int points = price.intValue();
        customer.addPoint(points);
        Rental rental = new Rental(movie, date, customer, price);
        customer.getLocations().add(rental);
        return rental;
    }

    private void ensureMinimumAge(Customer customer, Integer minimumAge) {
        if (customer.getAge(LocalDate.now()) < minimumAge) {
            throw new RuntimeException("le film ne peut être loué; trop jeune");
        }
    }

    public void bonusPoints(Customer customer, Movie movie){
        int nomberrOfactiveRental = customer.getLocations().stream()
                .filter(rental -> rental.isActive(LocalDateTime.now())).collect(Collectors.toList()).size();
        Rental rental = rent(customer, movie);
        if (nomberrOfactiveRental != 0 && rent(customer, movie) != null){
            customer.addPoint(customer.getPoints() * 2);
        }
    }






}

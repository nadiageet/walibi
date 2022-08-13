package movies.service;

import movies.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }


    public Rental rent(Customer customer, Movie movie, LocalDateTime rentalDate) {
        LocalDateTime expirationDate = expirationDate(rentalDate, customer);
        movie.getType().minimumAge().ifPresent(age -> ensureMinimumAge(customer, age));
        BigDecimal price = movie.getType().price();
        int points = computePoints(price, customer, rentalDate);
        customer.addPoint(points);

        Rental rental = new Rental(movie, rentalDate, customer, price, expirationDate);
        customer.getLocations().add(rental);

        rentalRepository.save(rental);
        return rental;
    }

    private static int computePoints(BigDecimal price, Customer customer, LocalDateTime rentalDate) {
        int points = price.intValue();
        int nomberrOfactiveRental = (int) customer.getLocations().stream()
                .filter(rental -> rental.isActive(rentalDate))
                .count();
        if (nomberrOfactiveRental > 0) {
            points *= 2;
        }
        return points;
    }

    private void ensureMinimumAge(Customer customer, Integer minimumAge) {
        if (customer.getAge(LocalDate.now()) < minimumAge) {
            throw new RuntimeException("le film ne peut être loué; trop jeune");
        }
    }

    public String printActiveRents(Customer customer, LocalDateTime date) {

        List<Rental> activeRents = customer.getActiveRents(date);
        if (activeRents.isEmpty()) {
            return "Aucune location en cours";
        }
        return activeRents.stream()
                .map(rental -> formatRentalMessage(date, rental))
                .collect(Collectors.joining("\n"));
    }

    private static String formatRentalMessage(LocalDateTime date, Rental rental) {
        return rental.getMovie().getTitle().toUpperCase() + " "
                + "prix : " + rental.getPrice() + "€, " +
                date.until(rental.getExpirationDate(), ChronoUnit.HOURS) + " hours left";
    }


    private LocalDateTime expirationDate(LocalDateTime rentedAt, Customer customer) {
        LocalDateTime expirationDate = rentedAt.plusHours(48);
        if (customer.isPrenium()) {
            expirationDate = rentedAt.plusHours(72);
        }
        return expirationDate;
    }

    public String rentalFrequencies() {
        List<Rental> rentals = rentalRepository.getAll();

        Map<Type, List<Rental>> rentalByType = rentals.stream()
                .collect(Collectors.groupingBy(r -> r.getMovie().getType()));

        return printFrequency(rentalByType);
    }

    private static String printFrequency(Map<Type, List<Rental>> locations) {
        String innerS = locations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue().size())
                .collect(Collectors.joining(", "));
        return "[" + innerS + "]";
    }

    private static List<Rental> filterRentsByTypes(List<Rental> rentals, Type type) {
        return rentals.stream()
                .filter(rental -> rental.getMovie().getType() == type)
                .collect(Collectors.toList());
    }

    public Rental gift(Customer buyer, Customer beneficiary, Movie movie, LocalDateTime rentalDate){
        LocalDateTime expirationDate = expirationDate(rentalDate, buyer);
        movie.getType().minimumAge().ifPresent(age -> ensureMinimumAge(beneficiary, age));
        BigDecimal price = movie.getType().price();
        int points = computePoints(price, buyer, rentalDate);
        buyer.addPoint(points);
        buyer.addGift();
        Rental rental = new Rental(movie, rentalDate, beneficiary, price, expirationDate);
        beneficiary.getLocations().add(rental);

        rentalRepository.save(rental);


        return rental;

    }

}

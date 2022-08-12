package movies;

import sun.util.resources.LocaleData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Rental {
    private Movie movie;
    private LocalDateTime rentedAt;

    private Customer customer;

    private BigDecimal price;


    public Rental(Movie movie, LocalDateTime rentedAt, Customer customer, BigDecimal price) {
        this.movie = movie;
        this.rentedAt = rentedAt;
        this.customer = customer;
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive(LocalDateTime now){

        long hours = rentedAt.until(now, ChronoUnit.HOURS);
        System.out.println(hours);
        LocalDateTime expirationDate = rentedAt.plusHours(48);
        if (customer.isPrenium()){
            expirationDate = rentedAt.plusHours(72);
        }
        return now.isBefore(expirationDate);
    }

    public Rental setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
        return this;
    }

}

package movies;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental {
    private Movie movie;
    private LocalDateTime rentedAt;

    private Customer customer;

    private BigDecimal price;
    private LocalDateTime expirationDate;


    public Rental(Movie movie, LocalDateTime rentedAt, Customer customer, BigDecimal price, LocalDateTime expirationDate) {
        this.movie = movie;
        this.rentedAt = rentedAt;
        this.customer = customer;
        this.price = price;
        this.expirationDate = expirationDate;
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

        return now.isBefore(expirationDate);
    }

    public Rental setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
        return this;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}

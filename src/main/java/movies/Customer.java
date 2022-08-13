package movies;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {

    private String nameOfClient;
    private List<Rental> locations = new ArrayList<>();

    private LocalDate birthDate;

    private int points = 0;

    public Customer(String nameOfClient, List<Rental> locations) {
        this.nameOfClient = nameOfClient;
        this.locations = locations;
    }

    public Customer(String name, LocalDate birthDate) {
        nameOfClient = name;
        this.birthDate = birthDate;
    }

    public String getNameOfClient() {
        return nameOfClient;
    }

    public List<Rental> getLocations() {
        return locations;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getAge(LocalDate now) {
        return birthDate.until(now).getYears();
    }

    public int getPoints() {
        return points;
    }

    public void addPoint(int point) {
        points += point;
    }

    public boolean isPrenium() {
        return this.getPoints() >= 30;
    }

    public List<Rental> getActiveRents(LocalDateTime date) {
        return this.locations.stream().filter(rental -> rental.isActive(date)).collect(Collectors.toList());
    }

    public String infos(Customer customer) {
        String infos = customer.nameOfClient;
        if (isPrenium()) {
            infos += " a un badge VIP et";
        }
        infos += " a " + locations.size() + " locations.";
        return infos;
    }
}

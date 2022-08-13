package movies;

import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    List<Rental> rentalList = new ArrayList<>();

    public void save(Rental rental) {
        rentalList.add(rental);
    }

    public List<Rental> getAll() {
        return rentalList;
    }


}

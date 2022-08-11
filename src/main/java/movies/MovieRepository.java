package movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRepository {
    Movie movie = new Movie("le silence des agneaux", Type.ADVENTURE);

    List<Movie> movies = Arrays.asList(
            new Movie("Bad Boy", Type.ACTION),
            new Movie("Astérix & Obélix - Mission Cléopâtre", Type.COMEDY));

    public List<Movie> getAllMovies(){
        return new ArrayList<Movie>();
    }
}

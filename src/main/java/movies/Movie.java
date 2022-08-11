package movies;

public class Movie {
    private String title;
    private Type type;

    public Movie(String title, Type type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }
}


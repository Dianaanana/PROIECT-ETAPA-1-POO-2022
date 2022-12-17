import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.MovieInput;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param movieInput
     */
    public Movie(final MovieInput movieInput) {
        this.name = movieInput.getName();
        this.actors = movieInput.getActors();
        this.year = movieInput.getYear();
        this.genres = movieInput.getGenres();
        this.countriesBanned = movieInput.getCountriesBanned();
        this.duration = movieInput.getDuration();
    }

    /**
     *
     * @param movie
     */
    public Movie(final Movie movie) {
        this.name = movie.getName();
        this.actors = movie.getActors();
        this.year = movie.getYear();
        this.genres = movie.getGenres();
        this.countriesBanned = movie.getCountriesBanned();
        this.duration = movie.getDuration();
    }
}

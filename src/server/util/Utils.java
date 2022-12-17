package server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.MovieInput;
import fileio.UserInput;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    /**
     * @param output
     * @param error
     * @param currentMoviesList
     * @param currentUser
     */
    public static void printStatus(final ArrayNode output,
                                   final String error,
                                   final List<MovieInput> currentMoviesList,
                                   final UserInput currentUser) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("error", error);
        outputNode.set("currentMoviesList", Mapper.mapMovieList(currentMoviesList));
        outputNode.set("currentUser", Mapper.mapUser(currentUser));
        output.add(outputNode);
    }

    /**
     * @param movies
     * @param currentUser
     * @return
     */
    public static List<MovieInput> getCurrentMoviesList(final ArrayList<MovieInput> movies,
                                                        final UserInput currentUser) {
        List<MovieInput> currentMovies = new ArrayList<>();
        if (movies == null || currentUser == null) {
            return currentMovies;
        }

        for (MovieInput movie : movies) {
            if (movie.getCountriesBanned().contains(currentUser.getCredentials().getCountry())) {
                continue;
            }

            currentMovies.add(movie);
        }

        return currentMovies;
    }

    public static MovieInput findMovie(final String movieName,
                                       final List<MovieInput> movies) {
        for (MovieInput movie : movies) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }

        return null;
    }
}

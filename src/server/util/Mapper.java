package server.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CredentialsInput;
import fileio.MovieInput;
import fileio.UserInput;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    /**
     *
     * @param movie
     * @return
     */
    public static JsonNode mapMovie(final MovieInput movie) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("name", movie.getName());
        node.put("year", movie.getYear());
        node.put("duration", movie.getDuration());
        node.set("actors", mapStringList(movie.getActors()));
        node.set("genres", mapStringList(movie.getGenres()));
        node.set("countriesBanned", mapStringList(movie.getCountriesBanned()));
        node.put("numLikes", movie.getNumLikes());
        node.put("rating", movie.getNumRatings() > 0 ? movie.getRating() / movie.getNumRatings() : 0);
        node.put("numRatings", movie.getNumRatings());
        return node;
    }

    /**
     *
     * @param stringList
     * @return
     */
    private static JsonNode mapStringList(final ArrayList<String> stringList) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (String s : stringList) {
            arrayNode.add(s);
        }

        return arrayNode;
    }

    /**
     *
     * @param user
     * @return
     */
    public static JsonNode mapUser(final UserInput user) {
        if (user == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.set("credentials", mapCredentials(user.getCredentials()));
        node.put("tokensCount", user.getTokensCount());
        node.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
        node.set("purchasedMovies", Mapper.mapMovieList(user.getPurchasedMovies()));
        node.set("watchedMovies", Mapper.mapMovieList(user.getWatchedMovies()));
        node.set("likedMovies", Mapper.mapMovieList(user.getLikedMovies()));
        node.set("ratedMovies", Mapper.mapMovieList(user.getRatedMovies()));
        return node;
    }

    /**
     *
     * @param credentials
     * @return
     */
    private static ObjectNode mapCredentials(final CredentialsInput credentials) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("name", credentials.getName());
        node.put("password", credentials.getPassword());
        node.put("accountType", credentials.getAccountType());
        node.put("country", credentials.getCountry());
        node.put("balance", Integer.toString(credentials.getBalance()));
        return node;
    }

    /**
     *
     * @param movies
     * @return
     */
    public static ArrayNode mapMovieList(final List<MovieInput> movies) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        if (movies == null) {
            return arrayNode;
        }

        for (MovieInput movie : movies) {
            arrayNode.add(Mapper.mapMovie(movie));
        }

        return arrayNode;
    }
}

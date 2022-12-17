package server.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CredentialsInput;
import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;
import server.ServerState;
import server.util.Constants;
import server.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SeeDetailsPage implements IPage {
    private String currentMovieName;

    /**
     * @param action
     * @param output
     * @param inputData
     * @return
     */
    @Override
    public boolean executeAction(final ActionsInput action,
                                 final ArrayNode output,
                                 final Input inputData) {
        if (action.getFeature().equals("purchase")) {
            return purchase(action, output, inputData);
        } else if (action.getFeature().equals("watch")) {
            return watch(action, output, inputData);
        } else if (action.getFeature().equals("like")) {
            return like(action, output, inputData);
        } else if (action.getFeature().equals("rate")) {
            return rate(action, output, inputData);
        }

        return false;
    }

    private boolean purchase(final ActionsInput action,
                             final ArrayNode output,
                             final Input inputData) {
        UserInput user = ServerState.getCurrentUser();
        CredentialsInput credentials = user.getCredentials();
        if (credentials.getAccountType().equals("premium") && user.getNumFreePremiumMovies() > 0) {
            user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
        } else {
            if (user.getTokensCount() < 2) {
                return false;
            }

            user.setTokensCount(user.getTokensCount() - Constants.MOVIE_COST);
        }

        MovieInput movie = Utils.findMovie(currentMovieName, inputData.getMovies());
        if (movie == null) {
            return false;
        }

        List<MovieInput> purchasedMovies = user.getPurchasedMovies();
        if (purchasedMovies.contains(movie)) {
            return false;
        }

        purchasedMovies.add(movie);
        List<MovieInput> singleMovieList = new ArrayList<>();
        singleMovieList.add(movie);
        Utils.printStatus(output, null, singleMovieList, ServerState.getCurrentUser());
        return true;
    }

    private boolean watch(final ActionsInput action,
                          final ArrayNode output,
                          final Input inputData) {
        MovieInput movie = Utils.findMovie(currentMovieName, inputData.getMovies());
        if (movie == null) {
            return false;
        }

        UserInput user = ServerState.getCurrentUser();
        if (!user.getPurchasedMovies().contains(movie)) {
            return false;
        }

        if (!user.getWatchedMovies().contains(movie)) {
            user.getWatchedMovies().add(movie);
        }

        List<MovieInput> singleMovieList = new ArrayList<>();
        singleMovieList.add(movie);
        Utils.printStatus(output, null, singleMovieList, ServerState.getCurrentUser());
        return true;
    }

    private boolean like(final ActionsInput action,
                         final ArrayNode output,
                         final Input inputData) {
        MovieInput movie = Utils.findMovie(currentMovieName, inputData.getMovies());
        if (movie == null) {
            return false;
        }

        UserInput user = ServerState.getCurrentUser();
        if (!user.getWatchedMovies().contains(movie)) {
            return false;
        }

        if (!user.getLikedMovies().contains(movie)) {
            user.getLikedMovies().add(movie);
            movie.setNumLikes(movie.getNumLikes() + 1);
        }

        List<MovieInput> singleMovieList = new ArrayList<>();
        singleMovieList.add(movie);
        Utils.printStatus(output, null, singleMovieList, ServerState.getCurrentUser());
        return true;
    }

    private boolean rate(final ActionsInput action,
                         final ArrayNode output,
                         final Input inputData) {
        if (action.getRate() < 1 || action.getRate() > Constants.MAX_RATING) {
            return false;
        }

        MovieInput movie = Utils.findMovie(currentMovieName, inputData.getMovies());
        if (movie == null) {
            return false;
        }

        UserInput user = ServerState.getCurrentUser();
        if (!user.getWatchedMovies().contains(movie)) {
            return false;
        }

        if (!user.getRatedMovies().contains(movie)) {
            user.getRatedMovies().add(movie);
            movie.setNumRatings(movie.getNumRatings() + 1);
            movie.setRating(movie.getRating() + action.getRate());
        }

        List<MovieInput> singleMovieList = new ArrayList<>();
        singleMovieList.add(movie);
        Utils.printStatus(output, null, singleMovieList, ServerState.getCurrentUser());
        return true;
    }

    /**
     * @param page
     * @param inputData
     * @param actionsInput
     * @return
     */
    @Override
    public boolean changePage(final String page,
                              final Input inputData,
                              final ArrayNode output,
                              final ActionsInput actionsInput) {
        return true;
    }

    /**
     * @param currentMovieName
     */
    public void setCurrentMovieName(final String currentMovieName) {
        this.currentMovieName = currentMovieName;
    }
}

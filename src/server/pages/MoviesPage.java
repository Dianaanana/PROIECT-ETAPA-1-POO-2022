package server.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;
import server.ServerState;
import server.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoviesPage implements IPage {

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
        if (action.getFeature().equals("search")) {
            List<MovieInput> filteredMovies =
                    searchMovies(
                        getCurrentMoviesList(inputData.getMovies(),
                        ServerState.getCurrentUser()),
                        action.getStartsWith()
                    );
            Utils.printStatus(output, null, filteredMovies, ServerState.getCurrentUser());
            return true;
        }
        // todo
        if (action.getFeature().equals("filter")) {
            ArrayList<MovieInput> filteredMovies =
                    getCurrentMoviesList(inputData.getMovies(), ServerState.getCurrentUser());
            if (action.getFilters().getSort() != null
                && action.getFilters().getSort().getRating() != null
            ) {
                filteredMovies = sortByRating(filteredMovies,
                    action.getFilters().getSort().getRating());
            }
            if (action.getFilters().getSort() != null
                && action.getFilters().getSort().getDuration() != null
            ) {
                filteredMovies = sortByDuration(filteredMovies,
                    action.getFilters().getSort().getDuration());
            }
            if (action.getFilters().getContains() != null
                && action.getFilters().getContains().getActors() != null
            ) {
                filteredMovies = containsActors(filteredMovies,
                    action.getFilters().getContains().getActors());
            }
            if (action.getFilters().getContains() != null
                && action.getFilters().getContains().getGenre() != null
            ) {
                filteredMovies = containsGenre(filteredMovies,
                    action.getFilters().getContains().getGenre());
            }
            Utils.printStatus(output, null, filteredMovies,
                    ServerState.getCurrentUser());
            return true;
        }
        return false;
    }

    /**
     * @param movies
     * @param genres
     * @return
     */
    private ArrayList<MovieInput> containsGenre(final ArrayList<MovieInput> movies,
                                                final ArrayList<String> genres) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
        currentMovies.addAll(movies);
        if (movies == null) {
            return currentMovies;
        }
        // todo
        for (MovieInput movie : movies) {
            for (String genre : genres) {
                if (!movie.getGenres().stream().anyMatch(str -> str.equals(genre))) {
                    currentMovies.remove(movie);
                }
            }
        }
        return currentMovies;
    }

    /**
     * @param movies
     * @param actors
     * @return
     */
    private ArrayList<MovieInput> containsActors(final ArrayList<MovieInput> movies, final ArrayList<String> actors) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
        currentMovies.addAll(movies);
        if (movies == null) {
            return currentMovies;
        }
        // todo
        for (MovieInput movie : movies) {
            for (String actor : actors) {
                if (!movie.getActors().stream().anyMatch(str -> str.equals(actor))) {
                    currentMovies.remove(movie);
                }
            }
        }
        return currentMovies;
    }

    /**
     * @param movies
     * @param order
     * @return
     */
    private ArrayList<MovieInput> sortByRating(final ArrayList<MovieInput> movies, final String order) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
        currentMovies.addAll(movies);
        if (movies == null) {
            return currentMovies;
        }
        if (order.equals("decreasing")) {
            Collections.sort(currentMovies, (a, b) -> (int) (b.getRating() - a.getRating()));
        } else {
            Collections.sort(currentMovies, (a, b) -> (int) (a.getRating() - b.getRating()));
        }

        return currentMovies;
    }

    /**
     * @param movies
     * @param order
     * @return
     */
    private ArrayList<MovieInput> sortByDuration(final ArrayList<MovieInput> movies, final String order) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
        currentMovies.addAll(movies);
        if (movies == null) {
            return currentMovies;
        }
        if (order.equals("decreasing")) {
            Collections.sort(currentMovies, (a, b) -> (int) (b.getDuration() - a.getDuration()));
        } else {
            Collections.sort(currentMovies, (a, b) -> (int) (b.getDuration() - a.getDuration()));
        }

        return currentMovies;
    }

    /**
     * @param movies
     * @param startsWith
     * @return
     */
    private ArrayList<MovieInput> searchMovies(final ArrayList<MovieInput> movies, final String startsWith) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
        if (movies == null || startsWith == null) {
            return currentMovies;
        }

        for (MovieInput movie : movies) {
            if (!movie.getName().startsWith(startsWith)) {
                continue;
            }
            currentMovies.add(movie);
        }

        return currentMovies;
    }

    /**
     * @param page
     * @param inputData
     * @param output
     * @param actionsInput
     * @return
     */
    @Override
    public boolean changePage(final String page, final Input inputData, final ArrayNode output, final ActionsInput actionsInput) {
        if (!page.equals("see details") && !page.equals("logout") && !page.equals("movies")) {
            return false;
        }

        if (page.equals("see details")) {
            List<MovieInput> currentMoviesList =
                    Utils.getCurrentMoviesList(inputData.getMovies(), ServerState.getCurrentUser());
            for (MovieInput movie : currentMoviesList) {
                if (movie.getName().equals(actionsInput.getMovie())) {
                    List<MovieInput> singleMovieList = new ArrayList<>();
                    singleMovieList.add(movie);
                    Utils.printStatus(output, null, singleMovieList, ServerState.getCurrentUser());
                    return true;
                }
            }

            //output.add("FFS " + currentMoviesList + " " + actionsInput.getMovie());
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param movies
     * @param currentUser
     * @return
     */
    private ArrayList<MovieInput> getCurrentMoviesList(final ArrayList<MovieInput> movies, final UserInput currentUser) {
        ArrayList<MovieInput> currentMovies = new ArrayList<>();
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
}

package fileio;

import server.util.Constants;

import java.util.ArrayList;
import java.util.List;

public final class UserInput {

    private CredentialsInput credentials;

    private int tokensCount;

    private int numFreePremiumMovies = Constants.MAX_FREE_MOVIES;

    private List<MovieInput> purchasedMovies = new ArrayList<>();

    private List<MovieInput> watchedMovies = new ArrayList<>();

    private List<MovieInput> likedMovies = new ArrayList<>();

    private List<MovieInput> ratedMovies = new ArrayList<>();

    /**
     *
     * @return
     */
    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public List<MovieInput> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final List<MovieInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public List<MovieInput> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final List<MovieInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public List<MovieInput> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final List<MovieInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public List<MovieInput> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final List<MovieInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}


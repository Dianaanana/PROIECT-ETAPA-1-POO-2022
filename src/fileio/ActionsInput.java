package fileio;

import java.util.ArrayList;

// TODO visitor pattern
public final class ActionsInput {
    private String type;
    private String page;
    private String feature;
    private String movie;
    private int count;
    private int rate;
    private CredentialsInput credentials;

    /**
     *
     */
    public final class FilterInput {
        public final class SortInput {
            private String rating;
            private String duration;

            public String getRating() {
                return rating;
            }

            public void setRating(final String rating) {
                this.rating = rating;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(final String duration) {
                this.duration = duration;
            }
        }

        /**
         *
         */
        public final class ContainsInput {
            private ArrayList<String> actors;
            private ArrayList<String> genre;

            public ArrayList<String> getActors() {
                return actors;
            }

            public void setActors(final ArrayList<String> actors) {
                this.actors = actors;
            }

            public ArrayList<String> getGenre() {
                return genre;
            }

            public void setGenre(final ArrayList<String> genre) {
                this.genre = genre;
            }
        }

        private SortInput sort;
        private ContainsInput contains;

        public SortInput getSort() {
            return sort;
        }

        public void setSort(final SortInput sort) {
            this.sort = sort;
        }

        public ContainsInput getContains() {
            return contains;
        }

        public void setContains(final ContainsInput contains) {
            this.contains = contains;
        }
    }

    private FilterInput filters;
    private String startsWith;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    public FilterInput getFilters() {
        return filters;
    }

    public void setFilters(final FilterInput filters) {
        this.filters = filters;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }


    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }
}

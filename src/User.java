public class User {
    private Credentials credentials;

    /**
     *
     * @return
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     *
     * @param credentials
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public User(final User user) {
        this.credentials = user.getCredentials();
    }
}

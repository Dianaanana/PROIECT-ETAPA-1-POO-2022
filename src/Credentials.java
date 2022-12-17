import fileio.CredentialsInput;

public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private int balance;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

    public Credentials(final CredentialsInput credentialsInput) {
        this.accountType = credentialsInput.getAccountType();
        this.balance = credentialsInput.getBalance();
        this.name = credentialsInput.getName();
        this.country = credentialsInput.getCountry();
        this.password = credentialsInput.getPassword();
    }

    public Credentials(final Credentials credentials) {
        this.accountType = credentials.getAccountType();
        this.balance = credentials.getBalance();
        this.name = credentials.getName();
        this.country = credentials.getCountry();
        this.password = credentials.getPassword();
    }
}

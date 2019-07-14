package UserTypes;

public abstract class CredentialedEntity implements java.io.Serializable{

    private String login;
    private String password;


    public CredentialedEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Confirms if this credentialed entity exists.
     */
    public boolean verifyLogin(String login, String password) {
        return login.equals(this.login) && password.equals(this.password);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    /**
     * Return the string representation of this CredentialedEntity's login.
     */
    @Override
    public String toString()
    {
        return login;
    }
}

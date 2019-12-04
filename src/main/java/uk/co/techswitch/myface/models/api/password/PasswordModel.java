package uk.co.techswitch.myface.models.api.password;

import uk.co.techswitch.myface.models.database.Password;

public class PasswordModel {

    private final Password password;

    public PasswordModel(Password password) {
        this.password = password;
    }

    public long getId() {
        return password.getId();
    }

    public Long getUserId() {
        return password.getUserId();
    }

    public String getHashPassword() {
        return password.getHashPassword();
    }

    public String getSalt() {return password.getSalt();}
}

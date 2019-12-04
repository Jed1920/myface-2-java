package uk.co.techswitch.myface.models.api.password;

import uk.co.techswitch.myface.models.database.Password;
import uk.co.techswitch.myface.models.database.User;

public class PasswordUserModel {

    private final Password password;
    private final User user;

    public PasswordUserModel(Password password, User user) {
        this.password = password;
        this.user = user;
    }

    public long getPasswordId() {
        return password.getId();
    }

    public Long getUserId() {
        return password.getUserId();
    }

    public String getHashPassword() {
        return password.getHashPassword();
    }

    public String getSalt() {return password.getSalt();}

    public long getId() {
        return user.getId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getDisplayName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}

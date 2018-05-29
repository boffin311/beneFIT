package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 30-05-2018.
 */

public class UserGoogleLogin {
    String email;
    String googletoken;

    public UserGoogleLogin(String email, String googletoken) {
        this.email = email;
        this.googletoken = googletoken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogletoken() {
        return googletoken;
    }

    public void setGoogletoken(String googletoken) {
        this.googletoken = googletoken;
    }
}

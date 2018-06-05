package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 30-05-2018.
 */

public class UserGoogleLogin {
    String email;
    String name;
    String googleToken;

    public UserGoogleLogin(String name,String email, String googletoken) {
        this.email = email;
        this.googleToken = googletoken;
        this.name =  name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogletoken() {
        return googleToken;
    }

    public void setGoogletoken(String googletoken) {
        this.googleToken = googletoken;
    }
}

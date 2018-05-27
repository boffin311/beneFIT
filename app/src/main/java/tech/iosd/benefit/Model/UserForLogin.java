package tech.iosd.benefit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM33R on 27-05-2018.
 */

public class UserForLogin {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public UserForLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

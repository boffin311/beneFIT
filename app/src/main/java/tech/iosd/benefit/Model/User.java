package tech.iosd.benefit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM33R on 25-05-2018.
 */

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String userName;
    @SerializedName("password")
    private String password;

    /*
    private String newPassword;
    private String token;
    private Integer age;
    private String heightCM;
    private String heightFT;
    private String weightKG;
    private String weightLB;
    private Integer lifestyleId;*/







  /*public String getWeightLB() {
        return weightLB;
    }

    public void setWeightLB(String weightLB) {
        this.weightLB = weightLB;
    }*/

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
/*
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }*/

}

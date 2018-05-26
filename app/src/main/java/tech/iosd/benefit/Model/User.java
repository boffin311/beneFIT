package tech.iosd.benefit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM33R on 25-05-2018.
 */

public class User {
    @SerializedName("username")
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

/*
    public Integer getLifestyleId() {
        return lifestyleId;
    }

    public void setLifestyleId(Integer lifestyleId) {
        this.lifestyleId = lifestyleId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeightCM() {
        return heightCM;
    }

    public void setHeightCM(String heightCM) {
        this.heightCM = heightCM;
    }

    public String getHeightFT() {
        return heightFT;
    }

    public void setHeightFT(String heightFT) {
        this.heightFT = heightFT;
    }

    public String getWeightKG() {
        return weightKG;
    }

    public void setWeightKG(String weightKG) {
        this.weightKG = weightKG;
    }

    public String getWeightLB() {
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

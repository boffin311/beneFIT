package tech.iosd.benefit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM33R on 27-05-2018.
 */

public class UserProfileUpdate {

    private int age;
    @SerializedName("height")
    private int heightCM;
    @SerializedName("weight")
    private int weightKG;
    private String gender;

    public UserProfileUpdate(int age, int heightCM, int weightKG, String gender) {
        this.age = age;
        this.heightCM = heightCM;
        this.weightKG = weightKG;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeightCM() {
        return heightCM;
    }

    public void setHeightCM(int heightCM) {
        this.heightCM = heightCM;
    }

    public int getWeightKG() {
        return weightKG;
    }

    public void setWeightKG(int weightKG) {
        this.weightKG = weightKG;
    }
}

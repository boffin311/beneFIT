package tech.iosd.benefit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SAM33R on 27-05-2018.
 */

public class UserProfileUpdate {

    private int age;

    private String gender;

    public class Measurements{
        public int height;
        public int weight;

        public Measurements(int height, int weight) {
            this.height = height;
            this.weight = weight;
        }

        public int getHeightCM() {
            return height;
        }

        public void setHeightCM(int heightCM) {
            this.height = heightCM;
        }

        public int getWeightKG() {
            return weight;
        }

        public void setWeightKG(int weightKG) {
            this.weight = weightKG;
        }
    }
    public Measurements measurements;

    public UserProfileUpdate(int age, int heightCM, int weightKG, String gender) {
        this.age = age;
        this.measurements=new Measurements(heightCM,weightKG);

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
        return measurements.height;
    }

    public void setHeightCM(int heightCM) {
        this.measurements.height = heightCM;
    }

    public int getWeightKG() {
        return measurements.weight;
    }

    public void setWeightKG(int weightKG) {
        this.measurements.weight = weightKG;
    }


}

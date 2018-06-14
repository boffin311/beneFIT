package tech.iosd.benefit.Model;

import java.util.Random;

/**
 * Created by SAM33R on 06-06-2018.
 */

public class Measurements {
    String date;
    int age;
    int height;
    int waist;
    int neck;
    int hip;
    int weight;
    public  String randomString() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public Measurements(int age, int height, int waist, int neck, int hip, int weight) {
        this.age = age;
        this.height = height;
        this.waist = waist;
        this.neck = neck;
        this.hip = hip;
        this.date = randomString();
        this.weight = weight;

    }

    public int getAge() {
        return age;
    }

    public void setAge(int weight) {
        this.age = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getNeck() {
        return neck;
    }

    public void setNeck(int neck) {
        this.neck = neck;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }
}

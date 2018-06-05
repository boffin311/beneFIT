package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 06-06-2018.
 */

public class Measurements {
    int age;
    int height;
    int waist;
    int neck;
    int hip;

    public Measurements(int age, int height, int waist, int neck, int hip) {
        this.age = age;
        this.height = height;
        this.waist = waist;
        this.neck = neck;
        this.hip = hip;
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

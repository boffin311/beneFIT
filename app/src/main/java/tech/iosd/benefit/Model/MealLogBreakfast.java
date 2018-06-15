package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class MealLogBreakfast {
    private ArrayList<MealLogFood> breakfast;
    private int breakfastCalorie;
    private int breakfastCarbs;
    private int breakfastFat;
    private int breakfastProtien;


    public ArrayList<MealLogFood> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<MealLogFood> breakfast) {
        this.breakfast = breakfast;
    }

    public int getBreakfastCalorie() {
        return breakfastCalorie;
    }

    public void setBreakfastCalorie(int breakfastCalorie) {
        this.breakfastCalorie = breakfastCalorie;
    }

    public int getBreakfastCarbs() {
        return breakfastCarbs;
    }

    public void setBreakfastCarbs(int breakfastCarbs) {
        this.breakfastCarbs = breakfastCarbs;
    }

    public int getBreakfastFat() {
        return breakfastFat;
    }

    public void setBreakfastFat(int breakfastFat) {
        this.breakfastFat = breakfastFat;
    }

    public int getBreakfastProtien() {
        return breakfastProtien;
    }

    public void setBreakfastProtien(int breakfastProtien) {
        this.breakfastProtien = breakfastProtien;
    }
}

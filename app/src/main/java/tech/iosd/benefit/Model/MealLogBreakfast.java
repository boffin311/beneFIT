package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class MealLogBreakfast {
    private ArrayList<MealLogFood> breakfast;
    private float breakfastCalorie;
    private float breakfastCarbs;
    private float breakfastFat;
    private float breakfastProtien;

    public MealLogBreakfast() {
        breakfastCalorie = 0;
        breakfastCarbs = 0;
        breakfastFat = 0;
        breakfastProtien = 0;
        breakfast =  new ArrayList<>();
    }

    public boolean addMeal(MealLogFood mealLogFood){
        breakfast.add(mealLogFood);
        return true;
    }
    public ArrayList<MealLogFood> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<MealLogFood> breakfast) {
        this.breakfast = breakfast;
    }

    public float getBreakfastCalorie() {
        return breakfastCalorie;
    }

    public void setBreakfastCalorie(float breakfastCalorie) {
        this.breakfastCalorie = breakfastCalorie;
    }

    public float getBreakfastCarbs() {
        return breakfastCarbs;
    }

    public void setBreakfastCarbs(float breakfastCarbs) {
        this.breakfastCarbs = breakfastCarbs;
    }

    public float getBreakfastFat() {
        return breakfastFat;
    }

    public void setBreakfastFat(float breakfastFat) {
        this.breakfastFat = breakfastFat;
    }

    public float getBreakfastProtien() {
        return breakfastProtien;
    }

    public void setBreakfastProtien(float breakfastProtien) {
        this.breakfastProtien = breakfastProtien;
    }
}

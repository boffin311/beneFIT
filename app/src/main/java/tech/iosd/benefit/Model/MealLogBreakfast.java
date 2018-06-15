package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class MealLogBreakfast {
    private ArrayList<ResponseForGetMeal.Food> breakfast;
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

    public boolean removeMealAt(int position){
        if (position<0 || position > breakfast.size() ){
            return false;
        }else {
            breakfast.remove(position);
            return true;
        }
    }

    public boolean addMeal(ResponseForGetMeal.Food Food){
        breakfast.add(Food);
        return true;
    }
    public ArrayList<ResponseForGetMeal.Food> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<ResponseForGetMeal.Food> breakfast) {
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

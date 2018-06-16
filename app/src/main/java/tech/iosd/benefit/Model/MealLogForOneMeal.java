package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class MealLogForOneMeal {
    private ArrayList<ResponseForGetMeal.Food> meal;
    private float mealCalorie;
    private float mealCarbs;
    private float mealFat;
    private float mealProtien;

    public MealLogForOneMeal() {
        mealCalorie = 0;
        mealCarbs = 0;
        mealFat = 0;
        mealProtien = 0;
        meal =  new ArrayList<>();
    }
    public void onDateChange(){
        mealCalorie = 0;
        mealCarbs = 0;
        mealFat = 0;
        mealProtien = 0;
        meal.clear();
    }

    public boolean removeMealAt(int position){
        if (position<0 || position > meal.size() ){
            return false;
        }else {
            meal.remove(position);
            return true;
        }
    }

    public boolean addMeal(ResponseForGetMeal.Food Food){
        meal.add(Food);
        return true;
    }
    public ArrayList<ResponseForGetMeal.Food> getMeal() {
        return meal;
    }

    public void setMeal(ArrayList<ResponseForGetMeal.Food> meal) {
        this.meal = meal;
    }

    public float getMealCalorie() {
        return mealCalorie;
    }

    public void setMealCalorie(float mealCalorie) {
        this.mealCalorie = mealCalorie;
    }

    public float getMealCarbs() {
        return mealCarbs;
    }

    public void setMealCarbs(float mealCarbs) {
        this.mealCarbs = mealCarbs;
    }

    public float getMealFat() {
        return mealFat;
    }

    public void setMealFat(float mealFat) {
        this.mealFat = mealFat;
    }

    public float getMealProtien() {
        return mealProtien;
    }

    public void setMealProtien(float mealProtien) {
        this.mealProtien = mealProtien;
    }
}

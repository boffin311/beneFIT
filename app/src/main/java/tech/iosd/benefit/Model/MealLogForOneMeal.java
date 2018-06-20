package tech.iosd.benefit.Model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import tech.iosd.benefit.Utils.Constants;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class MealLogForOneMeal {
    private ArrayList<ResponseForGetMeal.Food> meal;
    private float mealCalorie;
    private float mealCarbs;
    private float mealFat;
    private float mealProtien;
    private Context context;

    public MealLogForOneMeal(Context context) {
        mealCalorie = 0;
        mealCarbs = 0;
        mealFat = 0;
        mealProtien = 0;
        meal =  new ArrayList<>();
        this.context = context;
    }

    public void onDateChange(){
        mealCalorie = 0;
        mealCarbs = 0;
        mealFat = 0;
        mealProtien = 0;
        meal.clear();
    }

    public boolean removeMealAt(int position){
        if (/*position<0 || position > meal.size()*/false ){
            Toast.makeText(context,"not removing",Toast.LENGTH_SHORT).show();

            return false;
        }else {

            ResponseForGetMeal.Food food = meal.get(position);
            String unit = food.getUnit();
            int quantity = food.getQuantity();
            if(unit.equalsIgnoreCase("gram")){

                mealCalorie = mealCalorie - (quantity * food.getItem().getCalories())/food.getItem().getDefaultSize();
                mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs())/food.getItem().getDefaultSize();
                mealProtien = mealProtien - (quantity * food.getItem().getProteins())/food.getItem().getDefaultSize();
                mealFat = mealFat - (quantity * food.getItem().getFats())/food.getItem().getDefaultSize();

            }else if(unit.equalsIgnoreCase("piece")){

                mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
                mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
                mealProtien = mealProtien - (quantity * food.getItem().getProteins()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
                mealFat = mealFat - (quantity * food.getItem().getFats()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();

            }else if(unit.equalsIgnoreCase("bowl")){

                mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
                mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
                mealProtien = mealProtien - (quantity * food.getItem().getProteins()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
                mealFat = mealFat - (quantity * food.getItem().getFats()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();

            }else if(unit.equalsIgnoreCase("katori")){

                mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
                mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
                mealProtien = mealProtien - (quantity * food.getItem().getProteins()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
                mealFat = mealFat - (quantity * food.getItem().getFats()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();

            }else {

                mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
                mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
                mealProtien = mealProtien - (quantity * food.getItem().getProteins()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
                mealFat = mealFat - (quantity * food.getItem().getFats()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();

            }
            Toast.makeText(context,"removing cal: " + food.getItem().getCalories(),Toast.LENGTH_SHORT).show();

            meal.remove(position);
            return true;
        }
    }

    public boolean updateMealAt(ResponseForGetMeal.Food food,int newquantity,String newUnit,int position){

        String unit = food.getUnit();
        int quantity = food.getQuantity();
        if(unit.equalsIgnoreCase("gram")){

            mealCalorie = mealCalorie - (quantity * food.getItem().getCalories())/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs())/food.getItem().getDefaultSize();
            mealProtien = mealProtien - (quantity * food.getItem().getProteins())/food.getItem().getDefaultSize();
            mealFat = mealFat - (quantity * food.getItem().getFats())/food.getItem().getDefaultSize();

            calculateLogFor(newquantity,newUnit, food);


        }else if(unit.equalsIgnoreCase("piece")){

            mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealProtien = mealProtien - (quantity * food.getItem().getProteins()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealFat = mealFat - (quantity * food.getItem().getFats()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();

            calculateLogFor(newquantity,newUnit, food);

        }else if(unit.equalsIgnoreCase("bowl")){

            mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien - (quantity * food.getItem().getProteins()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat - (quantity * food.getItem().getFats()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();

            calculateLogFor(newquantity,newUnit, food);

        }else if(unit.equalsIgnoreCase("katori")){

            mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien - (quantity * food.getItem().getProteins()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat - (quantity * food.getItem().getFats()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();

            calculateLogFor(newquantity,newUnit, food);

        }else {

            mealCalorie = mealCalorie - (quantity * food.getItem().getCalories()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs - (quantity * food.getItem().getCarbs()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealProtien = mealProtien - (quantity * food.getItem().getProteins()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealFat = mealFat - (quantity * food.getItem().getFats()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            calculateLogFor(newquantity,newUnit, food);

        }
        food.setUnit(newUnit);
        food.setQuantity(newquantity);
        meal.get(position).setItem(food.getItem());
        meal.get(position).setQuantity(newquantity);
        meal.get(position).setUnit(newUnit);

        return true;


    }
    private void calculateLogFor(int quantity, String unit,ResponseForGetMeal.Food food ){
        if(unit.equalsIgnoreCase("gram")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories())/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs())/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins())/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats())/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("piece")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("bowl")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("katori")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();

        }else {

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();

        }
    }

    public boolean addMeal(ResponseForGetMeal.Food food,int quantity,String unit){
        food.setUnit(unit);
        food.setQuantity(quantity);

        if(unit.equalsIgnoreCase("gram")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories())/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs())/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins())/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats())/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("piece")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* food.getItem().getSize().piece)/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("bowl")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* Constants.BOWL_GRAM)/food.getItem().getDefaultSize();

        }else if(unit.equalsIgnoreCase("katori")){

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* Constants.KATORI_GRAM)/food.getItem().getDefaultSize();

        }else {

            mealCalorie = mealCalorie + (quantity * food.getItem().getCalories()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealCarbs = mealCarbs + (quantity * food.getItem().getCarbs()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealProtien = mealProtien + (quantity * food.getItem().getProteins()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();
            mealFat = mealFat + (quantity * food.getItem().getFats()* food.getItem().getSize().serve)/food.getItem().getDefaultSize();

        }
        meal.add(food);

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

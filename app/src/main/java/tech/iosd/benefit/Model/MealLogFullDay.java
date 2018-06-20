package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 11-06-2018.
 */

public class MealLogFullDay {
    private String date;
    private int totalCalorie;
    private int activity;
    private int remaining;

    private ArrayList<MealLogFood> breakfast;
    private int breakfastCalorie;
    private int breakfastCarbs;
    private int breakfastFat;
    private int breakfastProtien;

    private ArrayList<MealLogFood> midMorning;
    private int midMorningCalorie;
    private int midMorningCarbs;
    private int midMorningFat;
    private int midMorningProtien;

    private ArrayList<MealLogFood> lunch;
    private int lunchCalorie;
    private int lunchCarbs;
    private int lunchFat;
    private int lunchProtien;

    private ArrayList<MealLogFood> snack;
    private int snackCalorie;
    private int snackCarbs;
    private int snackFat;
    private int snackProtien;

    private ArrayList<MealLogFood> dinner;
    private int dinnerCalorie;
    private int dinnerCarbs;
    private int dinnerFat;
    private int dinner0Protien;



}

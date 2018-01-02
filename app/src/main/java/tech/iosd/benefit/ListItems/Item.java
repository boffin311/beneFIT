package tech.iosd.benefit.ListItems;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Anubhav on 02-01-2018.
 */
public class Item {

    //for folded state
    private String nameOfWorkout;
    private int requestsCount;

    //for unfolded state
    private String descriptionOfWorkout;
    private String avgDuration;
    private String avgCalorieSpent;

    private View.OnClickListener requestBtnClickListener;


    public Item(String nameOfWorkout, String descriptionOfWorkout, String avgDuration, String avgCalorieSpent) {
        this.nameOfWorkout = nameOfWorkout;
        this.descriptionOfWorkout = descriptionOfWorkout;
        this.avgDuration = avgDuration;
        this.avgCalorieSpent = avgCalorieSpent;
    }

    public String getDescriptionOfWorkout() {
        return descriptionOfWorkout;
    }

    public String getAvgDuration() {
        return avgDuration;
    }

    public String getAvgCalorieSpent() {
        return avgCalorieSpent;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public String getNameOfWorkout() {
        return nameOfWorkout;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (requestsCount != item.requestsCount) return false;
        if (descriptionOfWorkout != null ? !descriptionOfWorkout.equals(item.descriptionOfWorkout) : item.descriptionOfWorkout != null) return false;
        if (nameOfWorkout != null ? !nameOfWorkout.equals(item.nameOfWorkout) : item.nameOfWorkout != null)
            return false;
        if (avgCalorieSpent != null ? !avgCalorieSpent.equals(item.avgCalorieSpent) : item.avgCalorieSpent!= null)
            return false;
        if (avgDuration != null ? !avgDuration.equals(item.avgDuration) : item.avgDuration != null)
            return false;
//        if (a != null ? !date.equals(item.date) : item.date != null) return false;
        return false;

    }

    @Override
    public int hashCode() {
        int result = nameOfWorkout != null ? nameOfWorkout.hashCode() : 0;
        result = 31 * result + (avgDuration != null ? avgDuration.hashCode() : 0);
        result = 31 * result + (avgCalorieSpent != null ? avgCalorieSpent.hashCode() : 0);
        result = 31 * result + (descriptionOfWorkout != null ? descriptionOfWorkout.hashCode() : 0);
        result = 31 * result + requestsCount;

        return result;
    }


    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Full Body Workout","Description1", "1 hour", "10"));
        items.add(new Item("Abs Workout","Description2", "2 hour", "20"));
        items.add(new Item("Butt Workout","Description3", "3 hour", "30"));
        items.add(new Item("Belly Workout","Description4", "4 hour", "40"));
        return items;

    }

}


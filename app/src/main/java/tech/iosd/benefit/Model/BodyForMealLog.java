package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class BodyForMealLog {
    private String date;
    private String type;
    private ArrayList<Food> food;

    public BodyForMealLog(String date, String type, ArrayList<Food> food1) {
        this.date = date;
        this.type = type;
        this.food = food1;
    }

    public static class Food {
        private String item;
        private int quantity;

        public Food(String item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Food> getFood1() {
        return food;
    }

    public void setFood1(ArrayList<Food> food1) {
        this.food = food1;
    }
}

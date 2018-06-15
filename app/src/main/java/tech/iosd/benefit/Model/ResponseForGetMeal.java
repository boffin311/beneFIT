package tech.iosd.benefit.Model;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by SAM33R on 15-06-2018.
 */

public class ResponseForGetMeal {
    private boolean success;
    private String message;
    public Data data;

    private int __v;

    public class Data {
        public String _id;
        private String client;
        private String date;
        private String type;

        public ArrayList<food> food;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
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

        public ArrayList<ResponseForGetMeal.food> getFood() {
            return food;
        }

        public void setFood(ArrayList<ResponseForGetMeal.food> food) {
            this.food = food;
        }
    }
    public class food{
        private int quantity;
        private MealLogFood item;
        private String id;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public MealLogFood getItem() {
            return item;
        }

        public void setItem(MealLogFood food) {
            this.item = food;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 10-06-2018.
 */

public class ResponseForMesurementsHistory {

    public boolean success;
    public String message;
    public class Data{
        private String _id;
        private String name;
        private int __v;
        private int age;
        private int height;
        private int weight;
        private String gender;
        private String date;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }


    }
    public ArrayList<Data> data;

}

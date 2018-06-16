package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 11-06-2018.
 */

public class MealLogFood {


    private String _id;
    private String name;
    private float calories;
    private float proteins;
    private float fats;
    private float carbs;
    private float fibre;
    private float sugar;
    private String unit;

    private Size size;

    public class Size{
        //only one of the size will be used depending what is send from the server
        public int piece;
        public int bowl;
        public int katori;
        public int sever;

        public Size() {

        }
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFibre() {
        return fibre;
    }

    public void setFibre(float fibre) {
        this.fibre = fibre;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(Integer sugar) {
        this.sugar = sugar;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

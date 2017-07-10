package tech.iosd.benefit.ListItems;

/**
 * Created by anonymous on 7/7/17.
 */

public class WorkoutList {

    private String img_add, text, achiev_img;

    public WorkoutList(String img_add, String text, String achiev_img) {
        this.img_add = img_add;
        this.text = text;
        this.achiev_img = achiev_img;
    }

    public String getImg_add() {

        return img_add;
    }

    public String getText() {
        return text;
    }

    public String getAchiev_img() {
        return achiev_img;
    }
}

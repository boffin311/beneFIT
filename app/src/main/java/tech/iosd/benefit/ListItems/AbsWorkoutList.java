package tech.iosd.benefit.ListItems;

/**
 * Created by anonymous on 9/7/17.
 */

public class AbsWorkoutList {

    private String url, text;

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public AbsWorkoutList(String url, String text) {
        this.url = url;
        this.text = text;
    }
}

package tech.iosd.benefit.ListItems;

/**
 * Created by anonymous on 10/7/17.
 */

public class TrackDialogList {

    private String text, img_url;

    public String getText() {
        return text;
    }

    public String getImg_url() {
        return img_url;
    }

    public TrackDialogList(String text, String img_url) {

        this.text = text;
        this.img_url = img_url;
    }
}

package tech.iosd.benefit.ListItems;

/**
 * Created by anonymous on 10/7/17.
 */

public class TrackListitem {

    private String btnText, tvText, img_url, btnClr;

    public TrackListitem(String btnText, String tvText, String img_url, String btnClr) {
        this.btnText = btnText;
        this.tvText = tvText;
        this.img_url = img_url;
        this.btnClr = btnClr;
    }

    public String getBtnText() {
        return btnText;
    }

    public String getTvText() {
        return tvText;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getBtnClr() {
        return btnClr;
    }
}

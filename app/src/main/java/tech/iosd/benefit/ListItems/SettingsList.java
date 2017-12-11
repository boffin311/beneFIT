package tech.iosd.benefit.ListItems;

/**
 * Created by anonymous on 7/7/17.
 */

public class SettingsList {
    private String top, bottom;
    private boolean switch_visible;

    public String getTop() {
        return top;
    }

    public String getBottom() {
        return bottom;
    }

    public boolean getSwitchVisibility(){
        return switch_visible;
    }

    public SettingsList(String top, String bottom, boolean visibility) {
        this.top = top;
        this.bottom = bottom;
        this.switch_visible = visibility;
    }
}

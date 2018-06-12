package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 11-06-2018.
 */

public class ResponseForFoodSearch {
    public boolean success;

    public ArrayList<MealLogFood> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<MealLogFood> getData() {
        return data;
    }

    public void setData(ArrayList<MealLogFood> data) {
        this.data = data;
    }
}

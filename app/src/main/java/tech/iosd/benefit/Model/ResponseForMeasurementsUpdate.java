package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 06-06-2018.
 */

public class ResponseForMeasurementsUpdate {
    String success;
    String message;
    Measurements data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Measurements getData() {
        return data;
    }

    public void setData(Measurements data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

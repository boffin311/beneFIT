package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 16-06-2018.
 */

public class ResponseForSuccess {
    private boolean success;
    private String message;
    private Data data;
    private class Data{
        private String type;

        public String getType() {
            return type;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMealType() {
        return data.getType();
    }
}

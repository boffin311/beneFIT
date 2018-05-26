package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 25-05-2018.
 */

public class Response {

    private String message;
    private String success;

    public static class Token{
        public String token;
        public String expiresIn;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }
    }

    public Token token;


    public String getMessage() {
        return message;
    }

    public Token getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}

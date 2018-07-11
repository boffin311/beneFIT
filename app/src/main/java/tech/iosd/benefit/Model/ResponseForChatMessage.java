package tech.iosd.benefit.Model;

import java.util.ArrayList;

import tech.iosd.benefit.Message;

public class ResponseForChatMessage {

    private Boolean success;
    private ArrayList<MessageLocal> data;

    public ResponseForChatMessage(Boolean success, ArrayList<MessageLocal> messages) {
        this.success = success;
        this.data = messages;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<MessageLocal> getMessages() {
        return data;
    }

    public void setMessages(ArrayList<MessageLocal> messages) {
        this.data = messages;
    }

    public class MessageLocal{
        int author;
        String message;
        Long timestamp;

        public int getAuthor() {
            return author;
        }

        public void setAuthor(int author) {
            this.author = author;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}

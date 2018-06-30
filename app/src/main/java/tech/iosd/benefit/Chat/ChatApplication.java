package tech.iosd.benefit.Chat;

/**
 * Created by SAM33R on 23-06-2018.
 */

import android.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

import static tech.iosd.benefit.Utils.Constants.CHAT_SERVER_URL;


public class ChatApplication extends Application {

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}

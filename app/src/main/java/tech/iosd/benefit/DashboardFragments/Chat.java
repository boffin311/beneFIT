package tech.iosd.benefit.DashboardFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import tech.iosd.benefit.Adapters.MessageAdapter;
import tech.iosd.benefit.Author;
import tech.iosd.benefit.Chat.ChatApplication;
import tech.iosd.benefit.Chat.ChatDatabase;
import tech.iosd.benefit.Message;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.R;

public class Chat extends Fragment implements MessageInput.InputListener
{
    Context ctx;
    FragmentManager fm;
    ProgressDialog progressDialog;

    View rootView;

    String TAG = "tag";

    private DatabaseHandler db;


    private static final int REQUEST_LOGIN = 0;

    private static final int TYPING_TIMER_LENGTH = 600;

    public static final int ID_ME = 0;
    public static final int ID_COACH = 1;
    public static final int ID_NUTRITIONIST = 2;


    private MessagesList mMessagesView;
    private EditText mInputMessageView;
    //private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername;
    private Socket mSocket;

    private Boolean isConnected = false;
    private MessagesListAdapter<Message> adapter;
    private MessageInput messageInput;

    //if changing author details also change in chatDatabase getMessages()
    Author nutritionist = new Author("49", "Nutritionist", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/LetterN.svg/1200px-LetterN.svg.png");
    Author coach = new Author("50", "Coach", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Letter_c.svg/1200px-Letter_c.svg.png");
    Author me = new Author("51", "Me");

    ChatDatabase chatDatabase;

    private void attemptLogin() {

        Log.w("pkmn","attemptLogin");
        JWT jwt = new JWT(db.getUserToken());
        Claim claim = jwt.getClaim("email");
        String username =  claim.asString();

        mSocket.emit("add user", username);
        mUsername = username;
        mSocket.on("login", onLogin);

    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.w("pkmn","onLogin");
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }

            progressDialog.hide();
        }
    };

    private void addMessage(String message,int authorInt) {//adds a message on receiving and on sending
//        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
//                .username(username).message(message).build());
//        mAdapter.notifyItemInserted(mMessages.size() - 1);
        if(authorInt==ID_ME) {
            adapter.addToStart(new Message(String.valueOf(ID_ME), message, me, Calendar.getInstance().getTime()), true);
            messageInput.getInputEditText().setText("");

        }else if(authorInt==ID_COACH) {
            adapter.addToStart(new Message(String.valueOf(ID_COACH), message, coach, Calendar.getInstance().getTime()), true);
        }
        else if(authorInt==ID_NUTRITIONIST) {
            adapter.addToStart(new Message(String.valueOf(ID_NUTRITIONIST), message, nutritionist, Calendar.getInstance().getTime()), true);
        }
        //inserting to database
        chatDatabase.insertMessage(authorInt,message,Calendar.getInstance().getTimeInMillis());
    }

    private void attemptSend(String msg) {

        if(mUsername==null || !isConnected){
            Toast.makeText(getContext(),"Not Connected",Toast.LENGTH_SHORT).show();
            return;
        }

        String message = msg.trim();
        if(message.equals("")) return;

        JSONObject msgObj = new JSONObject();
        try {
            msgObj.put("username", mUsername);
            msgObj.put("message", message);
            addMessage(message, ID_ME);
            mSocket.emit("new message", msgObj);

        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.w("pkmn", "onConnect");
                        if (!isConnected) {
                            if (mUsername != null)
                                mSocket.emit("add user", mUsername);
                            showStatus(true);
                            isConnected = true;
                        }
                    }
                });
            }
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "diconnected");
                        showStatus(false);
                        isConnected = false;
                    }
                });
            }
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Error connecting");
                        showStatus(false);
                    }
                });
            }
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String message;
                        int authorInt;
                        try {

                            message = data.getString("message");
                            authorInt = data.getInt("author");

                            addMessage(message,authorInt);

                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                        }

                    }
                });
            }
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String username;
                        int numUsers;
                        try {
                            username = data.getString("username");
                            numUsers = data.getInt("numUsers");
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                            return;
                        }
                    }
                });
            }
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String username;
                        int numUsers;
                        try {
                            username = data.getString("username");
                            numUsers = data.getInt("numUsers");
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                            return;
                        }
                    }
                });
            }
        }
    };

    private void showStatus(Boolean connected){
        if(connected){
            TextView chatLabel = rootView.findViewById(R.id.chat_lbl);
            chatLabel.setText("Chat with your coach now");
            ImageView statusCircle = rootView.findViewById(R.id.status_circle);
            statusCircle.setColorFilter(Color.GREEN);
        }else {
            TextView chatLabel = rootView.findViewById(R.id.chat_lbl);
            chatLabel.setText("Cannot connect to chat");
            ImageView statusCircle = rootView.findViewById(R.id.status_circle);
            statusCircle.setColorFilter(Color.RED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_chat, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        ImageLoader imageLoader = new ImageLoader()
        {
            @Override
            public void loadImage(ImageView imageView, String url)
            {
                Picasso.with(ctx).load(url).into(imageView);
            }
        };

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Connecting..");
        progressDialog.setCancelable(false);
//        progressDialog.show();

        db = new DatabaseHandler(getContext());
        chatDatabase = new ChatDatabase(getContext());

        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();

        attemptLogin();

        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new message", onNewMessage);
        mSocket.on("user joined", onUserJoined);
        mSocket.on("user left", onUserLeft);
        mSocket.connect();

        adapter = new MessagesListAdapter<>("0", imageLoader);
        //Demo Code
        List<Message> messageList = chatDatabase.getMessages();

        if(messageList.size()>0){
            adapter.addToEnd(messageList,true);
        }else {
            adapter.addToStart(new Message(String.valueOf(ID_COACH), "Hey, I'm your coach", coach, Calendar.getInstance().getTime()), true);
            adapter.addToStart(new Message(String.valueOf(ID_NUTRITIONIST), "and I'm your nutritionist \nFeel free to ask anything :) \nYour fitness is our main concern :D", nutritionist, Calendar.getInstance().getTime()), true);
        }

        mMessagesView = rootView.findViewById(R.id.messagesList);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mMessagesView.setAdapter(mAdapter);
        mMessagesView.setAdapter(adapter);
        messageInput = rootView.findViewById(R.id.messages_input);
        //mInputMessageView = messageInput.getInputEditText();
        messageInput.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageInput.getInputEditText().getText().toString();
                if(!message.equals("")) {
                    attemptSend(message);
                }
            }
        });
        return rootView;
    }


    @Override
    public boolean onSubmit(CharSequence input) {
        return true;
    }

}

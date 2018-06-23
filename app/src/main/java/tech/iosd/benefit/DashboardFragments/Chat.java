package tech.iosd.benefit.DashboardFragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Calendar;

import io.socket.client.Socket;
import tech.iosd.benefit.Author;
import tech.iosd.benefit.Chat.ChatApplication;
import tech.iosd.benefit.Message;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.R;

public class Chat extends Fragment
{
    Context ctx;
    FragmentManager fm;
    ProgressDialog progressDialog;

    private String mUsername;

    private Socket mSocket;

    private DatabaseHandler db;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Connecting..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        db = new DatabaseHandler(getContext());

        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        attemptLogin();




    }
    private void attemptLogin() {


        mUsername = db.getUserGender();

        mSocket.emit("add user", username);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_chat, container, false);
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

        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>("0", imageLoader);

        //Demo Code
        Author coach = new Author("50", "Ankit Priyarup", "https://graph.facebook.com/100002080115387/picture?type=square");
        Author me = new Author("51", "Me");

        adapter.addToStart(new Message("0", "Hello", coach, Calendar.getInstance().getTime()), true);
        adapter.addToStart(new Message("1", "I'm your coach and i am here to help you out", coach, Calendar.getInstance().getTime()), true);
        //

        MessagesList messagesList = rootView.findViewById(R.id.messagesList);
        messagesList.setAdapter(adapter);
        return rootView;
    }
}

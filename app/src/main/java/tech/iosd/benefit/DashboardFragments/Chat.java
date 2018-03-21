package tech.iosd.benefit.DashboardFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Calendar;

import tech.iosd.benefit.Author;
import tech.iosd.benefit.Message;
import tech.iosd.benefit.R;

public class Chat extends Fragment
{
    Context ctx;
    FragmentManager fm;

    private final String senderId = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_fragment_chat, container, false);
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

        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);

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

package tech.iosd.benefit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.MessageContentType;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MessagesActivity extends AppCompatActivity implements MessagesListAdapter.OnLoadMoreListener
{
    private final String senderId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ImageLoader imageLoader = new ImageLoader()
        {
            @Override
            public void loadImage(ImageView imageView, String url)
            {
                Picasso.with(MessagesActivity.this).load(url).into(imageView);
            }
        };

        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);

        //Demo Code
        Author coach = new Author("50", "Ankit Priyarup", "https://graph.facebook.com/100002080115387/picture?type=square");
        Author me = new Author("51", "Me");

        adapter.addToStart(new Message("0", "Hello", coach, Calendar.getInstance().getTime()), true);
        adapter.addToStart(new Message("1", "I'm your coach and i am here to help you out", coach, Calendar.getInstance().getTime()), true);
        //

        MessagesList messagesList = findViewById(R.id.messagesList);
        messagesList.setAdapter(adapter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
    }
}
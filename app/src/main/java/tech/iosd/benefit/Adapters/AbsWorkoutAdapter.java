package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tech.iosd.benefit.ListItems.AbsWorkoutList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 9/7/17.
 */

public class AbsWorkoutAdapter extends RecyclerView.Adapter<AbsWorkoutAdapter.ViewHolder> {

    private List<AbsWorkoutList> lists;

    public AbsWorkoutAdapter(List<AbsWorkoutList> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abs_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._img.setImageBitmap(_load_img(lists.get(position).getUrl()));
        holder._text.setText(lists.get(position).getText());
        holder._abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast= Toast.makeText(context, "Content to be added :)", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private Bitmap _load_img(String url) {
        InputStream is = null;

        try {
            is = context.getAssets().open(url);
        }catch (IOException e){
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(is);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView _img;
        private TextView _text;
        private LinearLayout _abs;

        public ViewHolder(View itemView) {
            super(itemView);
            _img = (ImageView)itemView.findViewById(R.id.abs_img);
            _text = (TextView)itemView.findViewById(R.id.abs_text);
            _abs = (LinearLayout)itemView.findViewById(R.id.abs_layout);
        }
    }
}

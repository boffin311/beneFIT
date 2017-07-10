package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tech.iosd.benefit.ListItems.TrackListitem;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 10/7/17.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<TrackListitem> listitems;
    private Context context;

    public TrackAdapter(List<TrackListitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.track_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(load_image(listitems.get(position).getImg_url()));
        holder.textView.setText(listitems.get(position).getTvText());
        holder.button.setText(listitems.get(position).getBtnText());
    }

    private Bitmap load_image(String img_url) {
        InputStream is = null;
        try{
            is = context.getAssets().open(img_url);
        }catch (IOException e){
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.track_image);
            textView = (TextView)itemView.findViewById(R.id.track_text);
            button = (Button) itemView.findViewById(R.id.track_button);
        }
    }
}

package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tech.iosd.benefit.ListItems.TrackDialogList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 10/7/17.
 */

public class TrackDialogAdapter extends RecyclerView.Adapter<TrackDialogAdapter.ViewHolder> {

    private List<TrackDialogList> items;
    private Context context;

    public TrackDialogAdapter(List<TrackDialogList> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public TrackDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackDialogAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageBitmap(load_image(items.get(position).getImg_url()));
        holder.textView.setText(items.get(position).getText());

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
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private Switch aSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.track_dialog_text);
            imageView = (ImageView) itemView.findViewById(R.id.track_dialog_image);
            aSwitch = (Switch)itemView.findViewById(R.id.track_dialog_switch);
        }
    }
}

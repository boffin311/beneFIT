package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import tech.iosd.benefit.AbsWorkoutActivity;
import tech.iosd.benefit.ListItems.WorkoutList;
import tech.iosd.benefit.R;

import static android.view.View.GONE;

/**
 * Created by anonymous on 7/7/17.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<WorkoutList> workoutLists;
    boolean visible = false;
    private Context context;

    public WorkoutAdapter(List<WorkoutList> workoutLists, Context context) {
        this.workoutLists = workoutLists;
        this.context = context;
    }

    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_listitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkoutAdapter.ViewHolder holder, final int position) {
        holder.text.setText(workoutLists.get(position).getText());
        holder.main.setImageBitmap(load_main_img(workoutLists.get(position).getImg_add()));
        holder.achieve.setImageBitmap(load_achieve_img(workoutLists.get(position).getAchiev_img()));
        holder.workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position ==1){
                    context.startActivity(new Intent(context, AbsWorkoutActivity.class));
                }else {
                    Toast toast = Toast.makeText(context, "Working on it..", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        holder.desc.setVisibility(GONE);
        holder.more.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!visible){
                            visible = true;
                            holder.desc.setVisibility(View.VISIBLE);
                            holder.more.setText("Less");
                        }else {
                            holder.desc.setVisibility(GONE);
                            visible=false;
                            holder.more.setText("More");
                        }
                    }
                }
        );
    }

    private Bitmap load_achieve_img(String achiev_img) {
        InputStream is = null;
        try {
            is = context.getAssets().open(achiev_img);
        }catch (IOException e){
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

    private Bitmap load_main_img(String img_add) {
        InputStream is = null;
        try {
            is = context.getAssets().open(img_add);
        }catch (IOException e){
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public int getItemCount() {
        return workoutLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView main, achieve;
        private TextView text, more, desc;
        private LinearLayout workout;

        public ViewHolder(View itemView) {
            super(itemView);
            workout = (LinearLayout)itemView.findViewById(R.id.workout_layout);
            main = (ImageView)itemView.findViewById(R.id.workout_image);
            text = (TextView)itemView.findViewById(R.id.workout_name);
            achieve = (ImageView)itemView.findViewById(R.id.workout_achievment_img);
            more = (TextView)itemView.findViewById(R.id.workout_more);
            desc = (TextView)itemView.findViewById(R.id.workout_desc);
        }
    }
}

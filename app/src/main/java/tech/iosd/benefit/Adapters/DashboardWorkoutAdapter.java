package tech.iosd.benefit.Adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.Model.Exercise;
import tech.iosd.benefit.Model.ResponseForGetExcerciseVideoUrl;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;

/**
 * Created by SAM33R on 28-06-2018.
 */

public class DashboardWorkoutAdapter extends RecyclerView.Adapter<DashboardWorkoutAdapter.ViewHolder> {

    ArrayList<Exercise> exercises;
    Activity activity;
    CompositeSubscription compositeSubscription;
    DatabaseHandler db;
    int currentPosition =0;
    private Object video;
    private ThinDownloadManager downloadManager;

    public DashboardWorkoutAdapter(ArrayList<Exercise> exercises, Activity activity) {
        this.exercises = exercises;
        this.activity = activity;
        compositeSubscription =  new CompositeSubscription();
        db = new DatabaseHandler(activity.getApplicationContext());
        downloadManager =  new ThinDownloadManager();
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =inflater.inflate(R.layout.dashboard_my_workouts_list_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        DashboardWorkoutAdapter.ViewHolder viewHolder = new DashboardWorkoutAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (exercises.get(position).getExercise() != null)
        holder.name.setText(exercises.get(position).getExercise().getName());
        holder.details.setText(exercises.get(position).getReps() + " reps");
        holder.note.setText(exercises.get(position).get_id());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getApplicationContext(),"starting download",Toast.LENGTH_SHORT).show();
                /*File file = new File(activity.getCacheDir().toString());
                if(file.exists()){

                }
                else{

                }*/
                //getExcercise(exercises.get(position).getExercise().get_id());
            }
        });


    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name, details, note;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.dashboard_my_workouts_list_item_name);
            details = (TextView) itemView.findViewById(R.id.dashboard_my_workouts_list_item_sets_reps);
            view =  itemView.findViewById(R.id.dashboard_my_workouts_list_item_full_view);
            note =  (TextView) itemView.findViewById(R.id.dashboard_my_workouts_list_item_note);

        }
    }



}

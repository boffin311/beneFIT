package tech.iosd.benefit.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tech.iosd.benefit.Model.Exercise;
import tech.iosd.benefit.R;

/**
 * Created by SAM33R on 28-06-2018.
 */

public class DashboardWorkoutAdapter extends RecyclerView.Adapter<DashboardWorkoutAdapter.ViewHolder> {

    ArrayList<Exercise> exercises;
    Activity activity;

    public DashboardWorkoutAdapter(ArrayList<Exercise> exercises, Activity activity) {
        this.exercises = exercises;
        this.activity = activity;
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
        DashboardWorkoutAdapter.ViewHolder viewHolder = new DashboardWorkoutAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (exercises.get(position).getExercise() != null)
        holder.name.setText(exercises.get(position).getExercise().getName());
        holder.details.setText(exercises.get(position).getReps() + " reps");

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name, details;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.dashboard_my_workouts_list_item_name);
            details = (TextView) itemView.findViewById(R.id.dashboard_my_workouts_list_item_sets_reps);
        }
    }
}

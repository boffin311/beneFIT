package tech.iosd.benefit.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Model.MealLogFood;
import tech.iosd.benefit.R;

/**
 * Created by SAM33R on 11-06-2018.
 */
public class MealLog extends RecyclerView.Adapter<MealLog.ViewHolder> {
    private Context context;
    private ArrayList<MealLogFood> listItems;
    private Activity activity;

    public MealLog(Activity activity, ArrayList list){
        this.activity = activity;
        this.listItems = list;

    }

    @Override
    public MealLog.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater =activity.getLayoutInflater();
        View view =inflater.inflate(R.layout.list_row_meal_log, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealLog.ViewHolder holder, final int position) {


        holder.name.setText(listItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_row_meal_log_name_textview);



        }
    }
}
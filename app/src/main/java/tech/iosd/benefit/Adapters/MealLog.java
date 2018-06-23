package tech.iosd.benefit.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    private int itemSelected = -1;
    private AdapterCallback mAdapterCallback;


    public MealLog(Context context, ArrayList list, Activity activity, AdapterCallback c){
        this.context = context;
        this.listItems = list;
        this.activity = activity;
        this.mAdapterCallback = c;

    }



    @Override
    public MealLog.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view =inflater.inflate(R.layout.list_row_meal_log, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealLog.ViewHolder holder, final int position) {


        holder.name.setText(listItems.get(position).getName());
        holder.details.setText("1 "+listItems.get(position).getUnit());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelected = holder.getAdapterPosition();
                mAdapterCallback.newItemSelected(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, details;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_row_meal_log_name_textview);
            view = itemView.findViewById(R.id.list_row_meal_log_add_meal);
            details = (TextView) itemView.findViewById(R.id.list_row_meal_log_details_textview);

        }
    }
    public static interface AdapterCallback {
        void newItemSelected(int position);
    }
}
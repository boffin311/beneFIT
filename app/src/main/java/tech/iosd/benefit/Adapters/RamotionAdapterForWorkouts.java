package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import tech.iosd.benefit.ListItems.Item;
import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 02-01-2018.
 */

public class RamotionAdapterForWorkouts extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public RamotionAdapterForWorkouts(Context context, List<Item> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);

        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            cell = (FoldingCell) layoutInflater.inflate(R.layout.cell, parent, false);

            //view holder is used to bind views
            viewHolder.title = cell.findViewById(R.id.title_for_workout);
            viewHolder.avgCalorieSpent = cell.findViewById(R.id.average_calories_burnt);
            viewHolder.avgDuration = cell.findViewById(R.id.average_duration);
            viewHolder.descriptionOfWorkout = cell.findViewById(R.id.description_of_workout);
            viewHolder.contentRequestBtn = cell.findViewById(R.id.request_workout_btn);


            cell.setTag(viewHolder);

        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        //View holder is used to set values of the binded view
        viewHolder.avgDuration.setText(item.getAvgDuration());
        viewHolder.avgCalorieSpent.setText(item.getAvgCalorieSpent());
        viewHolder.descriptionOfWorkout.setText(item.getDescriptionOfWorkout());
        viewHolder.title.setText(item.getNameOfWorkout());


        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            viewHolder.contentRequestBtn.setOnClickListener(getDefaultRequestBtnClickListener());
        }


        return cell;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    private static class ViewHolder {
        TextView avgDuration;
        Button contentRequestBtn;
        TextView avgCalorieSpent;
        TextView title;
        TextView descriptionOfWorkout;
    }
}

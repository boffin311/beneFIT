package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.iosd.benefit.ListItems.MeasurementList;
import tech.iosd.benefit.ListItems.ProfileList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 7/7/17.
 */

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {

    List<MeasurementList> measurementLists;
    Context context;

    public MeasurementAdapter(List<MeasurementList> measurementLists, Context context) {
        this.measurementLists = measurementLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measurement_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder._left.setText(measurementLists.get(position).getLeft());
        holder._right.setText(measurementLists.get(position).getRight());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_data(measurementLists.get(position).getLeft());
            }
        });
    }

    private void change_data(String param) {
        Toast.makeText(context, "This function in not available yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return measurementLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _left, _right;
        private LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.measurement_item);
            _left = (TextView)itemView.findViewById(R.id.measurement_left);
            _right = (TextView)itemView.findViewById(R.id.measurement_right);
        }
    }
}

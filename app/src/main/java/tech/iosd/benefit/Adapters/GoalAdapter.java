package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.iosd.benefit.ListItems.GoalList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 7/7/17.
 */

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    List<GoalList> goalLists;
    Context context;

    public GoalAdapter(List<GoalList> goalLists, Context context) {
        this.goalLists = goalLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._left.setText(goalLists.get(position).getLeft());
        holder._right.setText(goalLists.get(position).getRight());
    }

    @Override
    public int getItemCount() {
        return goalLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _left, _right;

        public ViewHolder(View itemView) {
            super(itemView);
            _left = (TextView)itemView.findViewById(R.id.goal_left);
            _right = (TextView)itemView.findViewById(R.id.goal_right);
        }
    }
}

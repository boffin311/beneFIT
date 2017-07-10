package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.iosd.benefit.ListItems.GoalList;
import tech.iosd.benefit.ListItems.ProfileList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 7/7/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    List<ProfileList> profileLists;
    Context context;

    public ProfileAdapter(List<ProfileList> profileLists, Context context) {
        this.profileLists = profileLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._left.setText(profileLists.get(position).getLeft());
        holder._right.setText(profileLists.get(position).getRight());
    }

    @Override
    public int getItemCount() {
        return profileLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _left, _right;

        public ViewHolder(View itemView) {
            super(itemView);
            _left = (TextView)itemView.findViewById(R.id.profile_left);
            _right = (TextView)itemView.findViewById(R.id.profile_right);
        }
    }
}

package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import tech.iosd.benefit.ListItems.SettingsList;
import tech.iosd.benefit.R;

/**
 * Created by anonymous on 7/7/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    List<SettingsList> settingsLists;
    Context context;

    public SettingsAdapter(List<SettingsList> settingsLists, Context context) {
        this.settingsLists = settingsLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._top.setText(settingsLists.get(position).getTop());
        holder._bottom.setText(settingsLists.get(position).getBottom());
        if(settingsLists.get(position).getBottom().equals("")){
            holder._bottom.setVisibility(View.GONE);
        }
        if(!settingsLists.get(position).getSwitchVisibility()){
            holder.switchCompat.setEnabled(false);
            holder.switchCompat.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return settingsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _top, _bottom;
        private LinearLayout linearLayout;
        private SwitchCompat switchCompat;

        public ViewHolder(View itemView) {
            super(itemView);
            switchCompat = (SwitchCompat)itemView.findViewById(R.id.settings_switch);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.settings_item);
            _top = (TextView)itemView.findViewById(R.id.settings_top);
            _bottom = (TextView)itemView.findViewById(R.id.settings_bottom);
        }
    }
}

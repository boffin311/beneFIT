package tech.iosd.benefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.GoalAdapter;
import tech.iosd.benefit.Adapters.SettingsAdapter;
import tech.iosd.benefit.ListItems.GoalList;
import tech.iosd.benefit.ListItems.SettingsList;

public class SettingsActivity extends AppCompatActivity {

    private String[] top=
            {
                    "User Account",
                    "Password",
                    "Units",
                    "Inspire me!",
                    "Notifications",
                    "Detect Workouts",
                    "Reset Data",
                    "Help",
                    "About beneFIT"
            };
    private String[] bottom =
            {
                    "example@example.com",
                    "Protect your data using a password.",
                    "",
                    "Send me inspiring thoughts daily!",
                    "Show Notifications for various activities",
                    "Set your device to automatically detect workouts",
                    "Erase all of your data",
                    "",
                    ""
            };
    private boolean[] visibility =
            {
                    false,
                    false,
                    false,
                    true,
                    true,
                    true,
                    false,
                    false,
                    false
            };

    private RecyclerView recyclerView;
    private SettingsAdapter adapter;
    private List<SettingsList> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = (RecyclerView)findViewById(R.id.settings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(SettingsActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(SettingsActivity.this, DividerItemDecoration.VERTICAL));
        items = new ArrayList<>();
        for(int i=0;i<9;i++){
            SettingsList item = new SettingsList(top[i], bottom[i], visibility[i]);
            items.add(item);
        }
        adapter = new SettingsAdapter(items, SettingsActivity.this);
        recyclerView.setAdapter(adapter);
    }
}

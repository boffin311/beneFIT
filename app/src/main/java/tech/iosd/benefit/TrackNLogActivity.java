package tech.iosd.benefit;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.TrackAdapter;
import tech.iosd.benefit.Adapters.TrackDialogAdapter;
import tech.iosd.benefit.ListItems.TrackDialogList;
import tech.iosd.benefit.ListItems.TrackListitem;

public class TrackNLogActivity extends AppCompatActivity {

    private RecyclerView recyvlerView;
    private TrackAdapter adapter;
    private List<TrackListitem> items;

    private String[] text = {
            "Steps",
            "Running",
            "Cycling",
            "Food",
            "Water",
            "Caffine",
            "Weight",
            "Sleep",
            "Blood Pressure"
    };

    private String[] button = {
            "View",
            "Start",
            "Start",
            "Add",
            "+",
            "+",
            "Record",
            "Add Manually",
            "Record"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_n_log);

        recyvlerView = (RecyclerView)findViewById(R.id.track_recycler);
        recyvlerView.setHasFixedSize(true);
        recyvlerView.setLayoutManager(new LinearLayoutManager(TrackNLogActivity.this, LinearLayoutManager.HORIZONTAL, false));

        setData();
    }

    private void setData() {
        items = new ArrayList<>();
        for (int i=0; i<9;i++){
            TrackListitem item = new TrackListitem(button[i], text[i], "images/main.png", "");
            items.add(item);
        }

        adapter = new TrackAdapter(items, TrackNLogActivity.this);
        recyvlerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.track_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.track_ac_manage){
            Toast toast = Toast.makeText(TrackNLogActivity.this, "Choose what to \'track\'", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0 ,0);
            toast.show();

            show_options();
        }
        return super.onOptionsItemSelected(item);
    }

    private void show_options() {
        Dialog dialog = new Dialog(TrackNLogActivity.this);
        dialog.setContentView(R.layout.dialog_track);

        WindowManager.LayoutParams param = new WindowManager.LayoutParams();
        param.copyFrom(dialog.getWindow().getAttributes());
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(param);

        RecyclerView recycler = (RecyclerView)dialog.findViewById(R.id.track_dialog_recycler);
        recycler.addItemDecoration(new DividerItemDecoration(TrackNLogActivity.this, DividerItemDecoration.VERTICAL));
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(TrackNLogActivity.this));

        TrackDialogAdapter adapter;
        List<TrackDialogList> items;

        items = new ArrayList<>();
        for (int i=0; i<9;i++){
            TrackDialogList item = new TrackDialogList(text[i], "images/achieve.png");
            items.add(item);
        }

        adapter = new TrackDialogAdapter(items, TrackNLogActivity.this);
        recycler.setAdapter(adapter);
    }
}

package tech.iosd.benefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.AbsWorkoutAdapter;
import tech.iosd.benefit.ListItems.AbsWorkoutList;

public class AbsWorkoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AbsWorkoutAdapter adapter;
    private List<AbsWorkoutList> list;

    private String[] name ={
            "The Prone Plak",
            "The single-leg stretch",
            "Squat thrust with twist",
            "The cobra",
            "The climb up",
            "Abdominal move"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_workout);

        recyclerView = (RecyclerView)findViewById(R.id.abs_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(AbsWorkoutActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(AbsWorkoutActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        load_data();
    }

    private void load_data() {
        list = new ArrayList<>();
        for (int i=0;i<6;i++){
            AbsWorkoutList item = new AbsWorkoutList("images/main.png", name[i]);
            list.add(item);
        }
        adapter = new AbsWorkoutAdapter(list,AbsWorkoutActivity.this);
        recyclerView.setAdapter(adapter);
    }
}

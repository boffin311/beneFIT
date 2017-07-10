package tech.iosd.benefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.WorkoutAdapter;
import tech.iosd.benefit.ListItems.WorkoutList;

public class WorkoutActivity extends AppCompatActivity {

    private String[] text= {
            "Full Body Workout",
            "Abs Workout",
            "Butt Workout",
            "Belly Workout"
    };

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<WorkoutList> workoutLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        recyclerView = (RecyclerView)findViewById(R.id.workout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        load_data();
    }

    private void load_data() {
        workoutLists = new ArrayList<>();

        for (int i=0; i<4; i++){
            WorkoutList list = new WorkoutList("images/main.png", text[i], "images/achieve.png");
            workoutLists.add(list);
        }

        adapter = new WorkoutAdapter(workoutLists, WorkoutActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}

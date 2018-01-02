package tech.iosd.benefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.RamotionAdapterForWorkouts;
import tech.iosd.benefit.Adapters.WorkoutAdapter;
import tech.iosd.benefit.ListItems.Item;
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

        ListView listView = findViewById(R.id.mainListView);

        final ArrayList<Item> items = Item.getTestingList();


        final RamotionAdapterForWorkouts adapterForWorkouts = new RamotionAdapterForWorkouts(this, items);

        /**In future if someone wants to add custom click listeners*/

//        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
//        items.get(1).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
//        items.get(2).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
//        items.get(3).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
//        items.get(4).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
//        items.get(5).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
//            }
//        });
        adapterForWorkouts.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkoutActivity.this, "Should lead to a new activity which contains the workouts", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(adapterForWorkouts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ((FoldingCell)view).toggle(false);
                if (((FoldingCell) view).isUnfolded()) {
                    Log.d("UNFOLDTAG", ((FoldingCell) view).isUnfolded() + "");
                    ((FoldingCell) view).fold(false);
                } else {
                    Log.d("FOLDTAG", ((FoldingCell) view).isUnfolded() + "");
                    ((FoldingCell) view).unfold(false);

                    adapterForWorkouts.registerToggle(i);
                }
            }
        });
//        recyclerView = (RecyclerView)findViewById(R.id.workout_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        load_data();
    }

//    private void load_data() {
//        workoutLists = new ArrayList<>();
//
//        for (int i=0; i<4; i++){
//            WorkoutList list = new WorkoutList("images/main.png", text[i], "images/achieve.png");
//            workoutLists.add(list);
//        }
//
//        adapter = new WorkoutAdapter(workoutLists, WorkoutActivity.this);
//        recyclerView.setAdapter(adapter);
//    }

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

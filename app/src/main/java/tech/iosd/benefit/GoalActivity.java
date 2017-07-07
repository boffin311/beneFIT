package tech.iosd.benefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.GoalAdapter;
import tech.iosd.benefit.ListItems.GoalList;

public class GoalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GoalAdapter adapter;
    private List<GoalList> goalLists;

    private String[] leftitems =
            {
            "Current Weight",
            "Goal Weight",
            "Weekly Goal",
            "Activity Level"
    };

    private String[] rightitems =
            {
                    "83.3 kg",
                    "75 kg",
                    "Lose 1kg per week",
                    "Active"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        recyclerView = (RecyclerView)findViewById(R.id.goal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(GoalActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(GoalActivity.this, DividerItemDecoration.VERTICAL));
        goalLists = new ArrayList<>();
        for(int i=0;i<4;i++){
            GoalList goalList = new GoalList(leftitems[i], rightitems[i]);
            goalLists.add(goalList);
        }
        adapter = new GoalAdapter(goalLists, GoalActivity.this);
        recyclerView.setAdapter(adapter);
    }

}

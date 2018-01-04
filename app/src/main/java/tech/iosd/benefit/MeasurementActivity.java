package tech.iosd.benefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.MeasurementAdapter;
import tech.iosd.benefit.Adapters.ProfileAdapter;
import tech.iosd.benefit.ListItems.MeasurementList;
import tech.iosd.benefit.ListItems.ProfileList;

public class MeasurementActivity extends AppCompatActivity {

//    private String[] leftitems =
//            {
//                    "Gender",
//                    "Age",
//                    "Height",
//                    "Weight"
//            };
//
//    private String[] rightitems =
//            {
//                    "Male",
//                    "17",
//                    "160.0cm",
//                    "60.0kg"
//            };

//    private RecyclerView recyclerView;
//    private MeasurementAdapter adapter;
//    private List<MeasurementList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

//        recyclerView = (RecyclerView)findViewById(R.id.measurement_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MeasurementActivity.this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(MeasurementActivity.this, DividerItemDecoration.VERTICAL));
//
//        lists = new ArrayList<>();
//        for(int i=0;i<4;i++){
//            MeasurementList list = new MeasurementList(leftitems[i], rightitems[i]);
//            lists.add(list);
//        }
//        adapter = new MeasurementAdapter(lists, MeasurementActivity.this);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.measurement_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

package tech.iosd.benefit;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import tech.iosd.benefit.Fragments.BodyFatFragment;
import tech.iosd.benefit.Fragments.UserReportFragment;

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Measurements", BodyFatFragment.class)
                .add("Reports", UserReportFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);




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

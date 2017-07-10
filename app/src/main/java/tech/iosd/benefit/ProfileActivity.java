package tech.iosd.benefit;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.ProfileAdapter;
import tech.iosd.benefit.ListItems.ProfileList;

public class ProfileActivity extends AppCompatActivity {

    private String[] leftitems =
            {
                    "Gender",
                    "Date of birth",
                    "Height",
                    "Weight",
                    "Activity Level"
            };

    private String[] rightitems =
            {
                    "Male",
                    "01 Jan 2000",
                    "160.0cm",
                    "60.0kg",
                    "Little Activity"
            };

    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<ProfileList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("User Profile");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(ProfileActivity.this, DividerItemDecoration.VERTICAL));
        lists = new ArrayList<>();
        for(int i=0;i<4;i++){
            ProfileList list = new ProfileList(leftitems[i], rightitems[i]);
            lists.add(list);
        }
        adapter = new ProfileAdapter(lists, ProfileActivity.this);
        recyclerView.setAdapter(adapter);
    }
}

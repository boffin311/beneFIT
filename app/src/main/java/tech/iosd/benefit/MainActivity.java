package tech.iosd.benefit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import tech.iosd.benefit.MainFragments.Home;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View nav_header;
    private boolean ISHOMESHOWN;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.action_bar, null);
        TextView title = (TextView)v.findViewById(R.id.action_bar_title);
                title.setTypeface(
                        Typeface.createFromAsset(getAssets(), "fonts/Amarante-Regular.ttf")
                );
                title.setText(getTitle());
        getSupportActionBar().setCustomView(v);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_header = navigationView.inflateHeaderView(R.layout.activity_main_header);
        TextView txt =(TextView)nav_header.findViewById(R.id.appName);
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.ttf"));

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        display_selected_item(R.id.navigation_home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                        display_selected_item(R.id.navigation_home);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_profile:
                    startActivity(
                            new Intent(MainActivity.this, ProfileActivity.class)
                    );
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if(ISHOMESHOWN){
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Exit App?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                display_selected_item(R.id.navigation_home);
                            }
                        })
                        .create().show();
            }else {
                display_selected_item(R.id.navigation_home);
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void display_selected_item(int id) {

        ISHOMESHOWN = id == R.id.navigation_home;

        Fragment fragment = null;

        switch (id){
            case R.id.navigation_home:
                    fragment = new Home();
                break;
        }

        if(fragment!=null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, fragment).commit();
            transaction.addToBackStack("Main");
        }
    }

    public void workout_click(View view){
        startActivity(
                new Intent(MainActivity.this, WorkoutActivity.class)
        );
    }
    public void nutrition_click(View view){
        startActivity(
                new Intent(MainActivity.this, NutritionActivity.class)
        );
    }
    public void track_n_log_click(View view){
        startActivity(
                new Intent(MainActivity.this, TrackNLogActivity.class)
        );
    }
    public void measurement_click(View view){
        startActivity(
                new Intent(MainActivity.this, MeasurementActivity.class)
        );
    }
    public void goal_click(View view){
        startActivity(
                new Intent(MainActivity.this, GoalActivity.class)
        );
    }
    public void coach_click(View view){
        startActivity(
                new Intent(MainActivity.this, CoachActivity.class)
        );
    }
}


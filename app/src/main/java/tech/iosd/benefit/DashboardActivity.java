package tech.iosd.benefit;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import tech.iosd.benefit.DashboardFragments.Chat;
import tech.iosd.benefit.DashboardFragments.ChoosePlan;
import tech.iosd.benefit.DashboardFragments.Main;
import tech.iosd.benefit.DashboardFragments.Notification;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Context ctx;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx = this;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.dashboard_content, new Main()).commit();

        ImageView notificationsBtn = findViewById(R.id.navigation_dashboard_notification);
        notificationsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Notification()).addToBackStack(null).commit();
                FloatingActionButton contactBtn = findViewById(R.id.dashboard_contact);
                if(contactBtn != null)
                {
                    contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                    contactBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        ImageView coachBtn = findViewById(R.id.navigation_dashboard_coach);
        coachBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new ChoosePlan()).addToBackStack(null).commit();
                FloatingActionButton contactBtn = findViewById(R.id.dashboard_contact);
                if(contactBtn != null)
                {
                    contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                    contactBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
//        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

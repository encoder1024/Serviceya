package com.domo.zoom.serviceya;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout fabContainer;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private boolean fabMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabContainer = (LinearLayout) findViewById(R.id.fabContainerLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                toggleFabMenu();
            }
        });

        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu() {

        //Animations
        Animation show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        Animation hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        Animation show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        Animation hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        Animation show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        Animation hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        if (!fabMenuOpen) {
            fabContainer.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_close);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = 0;
            int endRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;


            ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    )
                    .setDuration(1000)
                    .start();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
            layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(show_fab_1);
            fab1.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin += (int) (fab2.getWidth() * 1.5);
            layoutParams.bottomMargin += (int) (fab2.getHeight() * 1.5);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(show_fab_2);
            fab2.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin += (int) (fab3.getWidth() * 0.25);
            layoutParams.bottomMargin += (int) (fab3.getHeight() * 1.7);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(show_fab_3);
            fab3.setClickable(true);
        } else {
            fab.setImageResource(R.drawable.ic_add);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;
            int endRadius = 0;

            Animator animator = ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    );
            animator.setDuration(1000);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fabContainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(hide_fab_1);
            fab1.setClickable(false);
            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab2.getWidth() * 1.5);
            layoutParams.bottomMargin -= (int) (fab2.getHeight() * 1.5);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(hide_fab_2);
            fab2.setClickable(false);
            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab3.getWidth() * 0.25);
            layoutParams.bottomMargin -= (int) (fab3.getHeight() * 1.7);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(hide_fab_3);
            fab3.setClickable(false);
        }
        fabMenuOpen = !fabMenuOpen;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

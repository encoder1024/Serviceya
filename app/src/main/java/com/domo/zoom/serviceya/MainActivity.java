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
import android.widget.TextView;

//Comento para alfa-release1.0
//Comento para alfa-release1.1

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout fabContainer;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5, fab6, fab7, fab8;
    private TextView tvFab1, tvFab2, tvFab3, tvFab4, tvFab5, tvFab6, tvFab8;
    private boolean fabMenuOpen = false;
    private boolean toolbarOpen = false;
    private Toolbar myToolbar;
    private boolean fab7Touch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabContainer = (LinearLayout) findViewById(R.id.fabContainerLayout);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                toggleFabMenu();
            }
        });

        fab1 = findViewById(R.id.fab_1);
        fab2 = findViewById(R.id.fab_2);
        fab3 = findViewById(R.id.fab_3);
        fab4 = findViewById(R.id.fab_4);
        fab5 = findViewById(R.id.fab_5);
        fab6 = findViewById(R.id.fab_6);
        fab7 = findViewById(R.id.fab_7);
        fab8 = findViewById(R.id.fab_8);
        tvFab1 = findViewById(R.id.tv_fab_1);
        tvFab2 = findViewById(R.id.tv_fab_2);
        tvFab3 = findViewById(R.id.tv_fab_3);
        tvFab4 = findViewById(R.id.tv_fab_4);
        tvFab5 = findViewById(R.id.tv_fab_5);
        tvFab6 = findViewById(R.id.tv_fab_6);
        tvFab8 = findViewById(R.id.tv_fab_8);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (!fab7Touch) {
                        toolbarOpen = !toolbarOpen;
                    } else {
                        fab7Touch = false;
                    }
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //TODO: probar definir el fab como si fuera la toolbar en la definicion de arriba.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab7Touch = true;
                toggleToolbar();
            }
        });


    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleToolbar() {
        if (!toolbarOpen) {
//            myToolbar.setVisibility(View.VISIBLE);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
        } else {
//            myToolbar.setVisibility(View.GONE);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        toolbarOpen = !toolbarOpen;

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
        Animation show_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        Animation hide_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);
        Animation show_fab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_show);
        Animation hide_fab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_hide);
        Animation show_fab_6 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab6_show);
        Animation hide_fab_6 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab6_hide);
        Animation show_fab_7 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab7_show);
        Animation hide_fab_7 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab7_hide);
        Animation show_fab_8 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab8_show);
        Animation hide_fab_8 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab8_hide);

        Animation show_tvfab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        Animation hide_tvfab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        Animation show_tvfab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        Animation hide_tvfab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        Animation show_tvfab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        Animation hide_tvfab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
        Animation show_tvfab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        Animation hide_tvfab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);
        Animation show_tvfab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_show);
        Animation hide_tvfab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_hide);
        Animation show_tvfab_6 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab6_show);
        Animation hide_tvfab_6 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab6_hide);
        Animation show_tvfab_8 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab8_show);
        Animation hide_tvfab_8 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab8_hide);


        if (!fabMenuOpen) {
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = 0;
            int endRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;


            Animator animator =ViewAnimationUtils
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
                            fabContainer.setVisibility(View.VISIBLE);
                            fab.setImageResource(R.drawable.ic_close);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            myToolbar.setVisibility(View.VISIBLE);
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
            layoutParams.rightMargin += (int) (fab1.getWidth() * 1.3);
            layoutParams.bottomMargin += (int) (fab1.getHeight() * 0);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(show_fab_1);
            fab1.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab1.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab1.getWidth() * 3.8);
            layoutParams.bottomMargin += (int) (tvFab1.getHeight() * 0);
            tvFab1.setLayoutParams(layoutParams);
            tvFab1.startAnimation(show_tvfab_1);
            tvFab1.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin += (int) (fab2.getWidth() * 1.1);
            layoutParams.bottomMargin += (int) (fab2.getHeight() * 1.1);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(show_fab_2);
            fab2.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab2.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab2.getWidth() * 2.4);
            layoutParams.bottomMargin += (int) (tvFab2.getHeight() * 4.3);
            tvFab2.setLayoutParams(layoutParams);
            tvFab2.startAnimation(show_tvfab_2);
            tvFab2.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin += (int) (fab3.getWidth() * 0);
            layoutParams.bottomMargin += (int) (fab3.getHeight() * 1.3);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(show_fab_3);
            fab3.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab3.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab3.getWidth() * 0);
            layoutParams.bottomMargin += (int) (tvFab3.getHeight() * 6.3);
            tvFab3.setLayoutParams(layoutParams);
            tvFab3.startAnimation(show_tvfab_3);
            tvFab3.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab4.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab4.getWidth() * 1.1);
            layoutParams.bottomMargin += (int) (fab4.getHeight() * 1.1);
            fab4.setLayoutParams(layoutParams);
            fab4.startAnimation(show_fab_4);
            fab4.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab4.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab4.getWidth() * 2.5);
            layoutParams.bottomMargin += (int) (tvFab4.getHeight() * 4.6);
            tvFab4.setLayoutParams(layoutParams);
            tvFab4.startAnimation(show_tvfab_4);
            tvFab4.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab5.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab5.getWidth() * 1.3);
            layoutParams.bottomMargin -= (int) (fab5.getHeight() * 0);
            fab5.setLayoutParams(layoutParams);
            fab5.startAnimation(show_fab_5);
            fab5.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab5.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab5.getWidth() * 5.0);
            layoutParams.bottomMargin -= (int) (tvFab5.getHeight() * 0);
            tvFab5.setLayoutParams(layoutParams);
            tvFab5.startAnimation(show_tvfab_5);
            tvFab5.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab6.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab6.getWidth() * 1.1);
            layoutParams.bottomMargin -= (int) (fab6.getHeight() * 1.1);
            fab6.setLayoutParams(layoutParams);
            fab6.startAnimation(show_fab_6);
            fab6.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab6.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab6.getWidth() * 1.3);
            layoutParams.bottomMargin -= (int) (tvFab6.getHeight() * 5.1);
            tvFab6.setLayoutParams(layoutParams);
            tvFab6.startAnimation(show_tvfab_6);
            tvFab6.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab7.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab7.getWidth() * 0);
            layoutParams.bottomMargin -= (int) (fab7.getHeight() * 1.3);
            fab7.setLayoutParams(layoutParams);
            fab7.startAnimation(show_fab_7);
            fab7.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab8.getLayoutParams();
            layoutParams.rightMargin += (int) (fab8.getWidth() * 1.1);
            layoutParams.bottomMargin -= (int) (fab8.getHeight() * 1.1);
            fab8.setLayoutParams(layoutParams);
            fab8.startAnimation(show_fab_8);
            fab8.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab8.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab8.getWidth() * 1.3);
            layoutParams.bottomMargin -= (int) (tvFab8.getHeight() * 5.1);
            tvFab8.setLayoutParams(layoutParams);
            tvFab8.startAnimation(show_tvfab_8);
            tvFab8.setClickable(true);





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
                    myToolbar.setVisibility(View.GONE);
                    tvFab1.setVisibility(View.INVISIBLE);
                    tvFab2.setVisibility(View.INVISIBLE);
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
            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.3);
            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(hide_fab_1);
            fab1.setClickable(false);
            layoutParams = (FrameLayout.LayoutParams) tvFab1.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab1.getWidth() * 3.8);
            layoutParams.bottomMargin -= (int) (tvFab1.getHeight() * 0);
            tvFab1.setLayoutParams(layoutParams);
            tvFab1.startAnimation(hide_tvfab_1);
            tvFab1.setClickable(false);

            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab2.getWidth() * 1.1);
            layoutParams.bottomMargin -= (int) (fab2.getHeight() * 1.1);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(hide_fab_2);
            fab2.setClickable(false);
            layoutParams = (FrameLayout.LayoutParams) tvFab2.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab2.getWidth() * 2.4);
            layoutParams.bottomMargin -= (int) (tvFab2.getHeight() * 4.3);
            tvFab2.setLayoutParams(layoutParams);
            tvFab2.startAnimation(hide_tvfab_2);
            tvFab2.setClickable(false);

            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab3.getWidth() * 0);
            layoutParams.bottomMargin -= (int) (fab3.getHeight() * 1.3);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(hide_fab_3);
            fab3.setClickable(false);
            layoutParams = (FrameLayout.LayoutParams) tvFab3.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab3.getWidth() * 0);
            layoutParams.bottomMargin -= (int) (tvFab3.getHeight() * 6.3);
            tvFab3.setLayoutParams(layoutParams);
            tvFab3.startAnimation(hide_tvfab_3);
            tvFab3.setClickable(false);

            layoutParams = (FrameLayout.LayoutParams) fab4.getLayoutParams();
            layoutParams.rightMargin += (int) (fab4.getWidth() * 1.1);
            layoutParams.bottomMargin -= (int) (fab4.getHeight() * 1.1);
            fab4.setLayoutParams(layoutParams);
            fab4.startAnimation(hide_fab_4);
            fab4.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab4.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab4.getWidth() * 2.5);
            layoutParams.bottomMargin -= (int) (tvFab4.getHeight() * 4.6);
            tvFab4.setLayoutParams(layoutParams);
            tvFab4.startAnimation(hide_fab_4);
            tvFab4.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab5.getLayoutParams();
            layoutParams.rightMargin += (int) (fab5.getWidth() * 1.3);
            layoutParams.bottomMargin += (int) (fab5.getHeight() * 0);
            fab5.setLayoutParams(layoutParams);
            fab5.startAnimation(hide_fab_5);
            fab5.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab5.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab5.getWidth() * 5.0);
            layoutParams.bottomMargin += (int) (tvFab5.getHeight() * 0);
            tvFab5.setLayoutParams(layoutParams);
            tvFab5.startAnimation(hide_tvfab_5);
            tvFab5.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab6.getLayoutParams();
            layoutParams.rightMargin += (int) (fab6.getWidth() * 1.1);
            layoutParams.bottomMargin += (int) (fab6.getHeight() * 1.1);
            fab6.setLayoutParams(layoutParams);
            fab6.startAnimation(hide_fab_6);
            fab6.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab6.getLayoutParams();
            layoutParams.rightMargin += (int) (tvFab6.getWidth() * 1.3);
            layoutParams.bottomMargin += (int) (tvFab6.getHeight() * 5.1);
            tvFab6.setLayoutParams(layoutParams);
            tvFab6.startAnimation(hide_tvfab_6);
            tvFab6.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab7.getLayoutParams();
            layoutParams.rightMargin += (int) (fab7.getWidth() * 0);
            layoutParams.bottomMargin += (int) (fab7.getHeight() * 1.3);
            fab7.setLayoutParams(layoutParams);
            fab7.startAnimation(hide_fab_7);
            fab7.setClickable(true);

            layoutParams = (FrameLayout.LayoutParams) fab8.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab8.getWidth() * 1.1);
            layoutParams.bottomMargin += (int) (fab8.getHeight() * 1.1);
            fab8.setLayoutParams(layoutParams);
            fab8.startAnimation(hide_fab_8);
            fab8.setClickable(true);
            layoutParams = (FrameLayout.LayoutParams) tvFab8.getLayoutParams();
            layoutParams.rightMargin -= (int) (tvFab8.getWidth() * 1.3);
            layoutParams.bottomMargin += (int) (tvFab8.getHeight() * 5.1);
            tvFab8.setLayoutParams(layoutParams);
            tvFab8.startAnimation(hide_tvfab_8);
            tvFab8.setClickable(true);

            //TODO: colocar un boton en el menú que muestre la Toolbar.5
        }
        fabMenuOpen = !fabMenuOpen;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            toolbarOpen = false;
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

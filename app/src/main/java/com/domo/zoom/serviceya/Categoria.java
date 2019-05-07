package com.domo.zoom.serviceya;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Categoria extends AppCompatActivity {

    private List<ItemPrestador> presList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PrestadorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rv_prestadores);

        mAdapter = new PrestadorAdapter(presList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void prepareMovieData() {
        ItemPrestador item = new ItemPrestador("Mad Max: Fury Road", "Action & Adventure", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Inside Out", "Animation, Kids & Family", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Star Wars: Episode VII - The Force Awakens", "Action", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Shaun the Sheep", "Animation", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("The Martian", "Science Fiction & Fantasy", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Mission: Impossible Rogue Nation", "Action", "2015", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Up", "Animation", "2009", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Star Trek", "Science Fiction", "2009", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("The LEGO Movie", "Animation", "2014", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Iron Man", "Action & Adventure", "2008", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Aliens", "Science Fiction", "1986", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Chicken Run", "Animation", "2000", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Back to the Future", "Science Fiction", "1985", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Raiders of the Lost Ark", "Action & Adventure", "1981", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Goldfinger", "Action & Adventure", "1965", "Viva la vida.");
        presList.add(item);

        item = new ItemPrestador("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", "Viva la vida.");
        presList.add(item);

        mAdapter.notifyDataSetChanged();
    }
}

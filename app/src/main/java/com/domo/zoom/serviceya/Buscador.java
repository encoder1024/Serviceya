package com.domo.zoom.serviceya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;

public class Buscador extends AppCompatActivity {

    private AutoCompleteTextView acQueBuscas;
    private Spinner spProvincia, spLocalidad, spGrupo, spCategoria;
    private CheckBox cbProvincia, cbLocalidad, cbGrupo, cbCategoria;
    private RecyclerView rvPresEncontrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the current intent
        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.
        String que_buscas = intent.getStringExtra("QUE_NECESITAS");

        acQueBuscas = findViewById(R.id.acQueBuscas);

        spProvincia = findViewById(R.id.spProvincia);
        spLocalidad = findViewById(R.id.spLocalidad);
        spGrupo = findViewById(R.id.spSector);
        spCategoria = findViewById(R.id.spEspecialidad);

        cbProvincia = findViewById(R.id.cbProvincia);
        cbLocalidad = findViewById(R.id.cbLocalidad);
        cbGrupo = findViewById(R.id.cbSector);
        cbCategoria = findViewById(R.id.cbEspecialidad);

        rvPresEncontrados = findViewById(R.id.rvPrestEncontrados);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        acQueBuscas.setText(que_buscas);




    }

}

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
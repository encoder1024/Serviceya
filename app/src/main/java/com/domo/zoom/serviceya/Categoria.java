package com.domo.zoom.serviceya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categoria extends AppCompatActivity {

    private List<ItemPrestador> presList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PrestadorAdapter mAdapter;
    private Spinner spCategoria;
    private ArrayList<String> categorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: tomar el valor del botón del menú con el grupo elegido para poder buscar las categorias en la base y cargarlos en el spinner.
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //get the current intent
        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.
        String grupo_name = intent.getStringExtra("GRUPO_NAME");

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle("Especialidad: " + grupo_name);
        }


        recyclerView = findViewById(R.id.rv_prestadores);

        mAdapter = new PrestadorAdapter(presList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        buscarCategorias(grupo_name);


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
    private void actualizarPrestadores(String categoriaName) {
//        ItemPrestador item = new ItemPrestador("Mad Max: Fury Road", "Action & Adventure", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Inside Out", "Animation, Kids & Family", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Star Wars: Episode VII - The Force Awakens", "Action", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Shaun the Sheep", "Animation", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("The Martian", "Science Fiction & Fantasy", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Mission: Impossible Rogue Nation", "Action", "2015", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Up", "Animation", "2009", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Star Trek", "Science Fiction", "2009", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("The LEGO Movie", "Animation", "2014", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Iron Man", "Action & Adventure", "2008", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Aliens", "Science Fiction", "1986", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Chicken Run", "Animation", "2000", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Back to the Future", "Science Fiction", "1985", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Raiders of the Lost Ark", "Action & Adventure", "1981", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Goldfinger", "Action & Adventure", "1965", "Viva la vida.");
//        presList.add(item);
//
//        item = new ItemPrestador("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", "Viva la vida.");
//        presList.add(item);

        mAdapter.notifyDataSetChanged();
    }

    private void buscarCategorias(String grupoName) {

        //TODO
        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_CATEGORIAS + grupoName, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == Constants.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == Constants.CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }

        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            String[] urlmix = url.split("=");
            try {
                switch (urlmix[0] + "=" + urlmix[1] + "=") {
                    case Api.URL_READ_SITIOS_FULL:
                        JSONObject objectSitio = new JSONObject(s);
                        if (!objectSitio.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectSitio.getString("message") + ": sitio!", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            //refreshSitesList(objectSitio.getJSONArray("sitio"));
                        }
                        break;
                    case Api.URL_READ_CATEGORIAS:
                        JSONObject objectEspecial = new JSONObject(s);
                        if (!objectEspecial.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectEspecial.getString("message") + ": categoria", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            refreshCategorias(objectEspecial.getJSONArray("categorias"));
                        }
                        break;
                    case Api.URL_READ_SITIO_CERTIF:
                        JSONObject objectUser = new JSONObject(s);
                        if (!objectUser.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectUser.getString("message") + ": certificados!", Toast.LENGTH_SHORT).show();
                            //refreshCertifList(objectUser.getJSONArray("certif"));
                        }
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshCategorias(JSONArray categoriasJson) throws JSONException {

        categorias.clear();

        for (int i = 0; i < categoriasJson.length(); i++){
            JSONObject obj = categoriasJson.getJSONObject(i);
            categorias.add(obj.get("categoria").toString());
        }

        spCategoria = findViewById(R.id.sp_categorias);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //actualizarPrestadores(spCategoria.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateFoto(String fotoNombre) {
        String URL = Api.ROOT_URL_IMAGES+fotoNombre;
        CargaImagenes nuevaTarea = new CargaImagenes();
        nuevaTarea.execute(URL);
    }

    private class CargaImagenes extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(Categoria.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            String url = params[0];
            return descargarImagen(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            //myLogo.setImageBitmap(result); TODO: actualizar la imagen de cada objeto Prestador.
            pDialog.dismiss();
        }

    }

    private Bitmap descargarImagen (String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }

}

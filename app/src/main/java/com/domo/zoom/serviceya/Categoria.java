package com.domo.zoom.serviceya;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categoria extends AppCompatActivity {

    private List<ItemPrestador> presList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PrestadorAdapter mAdapter;
    private Spinner spCategoria;
    private ArrayList<String> categorias = new ArrayList<>();
    private ArrayList<Prestador> prestadores = new ArrayList<>();
    private String categoriaSelected, grupo_name;


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
        grupo_name = intent.getStringExtra("GRUPO_NAME");

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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
//                Toast.makeText(Categoria.this, "Single Click on position :"+position,
//                        Toast.LENGTH_SHORT).show();
                ImageView picture=(ImageView)view.findViewById(R.id.imageView2);
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Categoria.this, "Single Click on Image :"+ position,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getBaseContext(), PrestadorShow.class);
                //attach the key value pair using putExtra to this intent
                intent.putExtra("PRESTADOR_ID", prestadores.get(position).getId());
                intent.putExtra("PRESTADOR_NOMBRE",prestadores.get(position).getNombre() + " " + prestadores.get(position).getApellido());
                intent.putExtra("PRESTADOR_PHONE", prestadores.get(position).getCelular());
                intent.putExtra("PRESTADOR_WEB", prestadores.get(position).getWeb());
                intent.putExtra("PRESTADOR_EMAIL", prestadores.get(position).getEmail());
                intent.putExtra("PRESTADOR_IMAGEN", prestadores.get(position).getImagen());
                intent.putExtra("PRESTADOR_GRUPO", grupo_name);
                intent.putExtra("PRESTADOR_CATEGORIA", categoriaSelected);
                intent.putExtra("PRESTADOR_FROM", "Categoria");
                //starting the activity
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(Categoria.this, "Long press on position :"+position,
//                        Toast.LENGTH_LONG).show();
            }
        }));

        buscarCategorias(grupo_name);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Categoria.this, "DEBE HACER CLICK en Enviar ->...", Snackbar.LENGTH_LONG)
                        .show();
                String [] to = {"hola@prontoservice.com.ar"};
                String [] cco = {"hola@prontoservice.com.ar", "hola@prontoservice.com.ar", "hola@prontoservice.com.ar", "hola@prontoservice.com.ar", "hola@prontoservice.com.ar", "hola@prontoservice.com.ar", "hola@prontoservice.com.ar"};
                int i = 0;
                for ( Prestador prestador : prestadores ) {
                    cco[i] = prestador.getEmail();
                    i++;
                    if (i > cco.length) break;
                }
                String subject = "Solicitud de Asesoramiento Abierta";
                String menssage = "Favor contactar por email o por teléfono, " +
                        MainActivity.pref.getString(Constants.KEY_USER_CELULAR, "") +
                        ".\n" +
                        "Saludos.\n\n" +
                        MainActivity.pref.getString(Constants.KEY_USER_NOMBRE, "");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_BCC, cco);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, menssage);

                //startActivity(Intent.createChooser(emailIntent, "Email "));

                //emailIntent.setData(Uri.parse("mailto:hola@prontoservice.com.ar?&bcc="+ Uri.encode(cco)));
                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                    finish();
                    moveTaskToBack(true);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void actualizarPrestadores(String categoriaName) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_PRESTADORES + categoriaName,
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void buscarCategorias(String grupoName) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_CATEGORIAS + grupoName + "&queBusco=" + "milmil",
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
//                            Toast.makeText(getApplicationContext(), objectEspecial.getString("message") + ": categoria", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            refreshCategorias(objectEspecial.getJSONArray("categorias"));
                        }
                        break;
                    case Api.URL_READ_PRESTADORES:
                        JSONObject objectUser = new JSONObject(s);
                        if (!objectUser.getBoolean("error")) {
//                            Toast.makeText(getApplicationContext(), objectUser.getString("message") + ": prestadores!", Toast.LENGTH_SHORT).show();
                            refreshPresList(objectUser.getJSONArray("prestadores"));
                        }
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshPresList(JSONArray prestadoresJson) throws JSONException {

        prestadores.clear();
        presList.clear();

        for (int i = 0; i < prestadoresJson.length(); i++){
            JSONObject obj = prestadoresJson.getJSONObject(i);
            prestadores.add(new Prestador(
                obj.getInt("id"),
                obj.getString("nombre"),
                obj.getString("apellido"),
                obj.getString("token"),
                obj.getString("telefono"),
                obj.getString("celular"),
                obj.getString("email"),
                obj.getString("web"),
                obj.getString("password"),
                obj.getString("imagen"),
                obj.getString("direccion"),
                obj.getInt("habilitado"),
                obj.getInt("estado"),
                obj.getString("created_at"),
                obj.getString("updated_at")
            ));

            presList.add(new ItemPrestador(
                    obj.getString("imagen"),
                    obj.getString("nombre")+" "+obj.getString("apellido"),
                    "Calificación en proceso...",
                    "Miembro desde " + obj.getString("created_at")
            ));
        }



       mAdapter.notifyDataSetChanged();

    }

    private void refreshCategorias(JSONArray categoriasJson) throws JSONException {

        categorias.clear();

        for (int i = 0; i < categoriasJson.length(); i++){
            JSONObject obj = categoriasJson.getJSONObject(i);
            if (i>0){
                if (obj.get("categoria").toString().equals(categorias.get(categorias.size()-1))){
                    continue;
                }
            }
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

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //((TextView) adapterView.getChildAt(0)).setTextSize(5);
                categoriaSelected = spCategoria.getItemAtPosition(i).toString();
                actualizarPrestadores(categoriaSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * RecyclerView: Implementing single item click and long press
     *
     * - creating an Interface for single tap and long press
     * - Parameters are its respective view and its position
     * */

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Categoria.this,
                "Por favor usar la flecha de regreso, arriba a la izquierda...", Toast.LENGTH_LONG)
                .show();
    }

}



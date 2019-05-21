package com.domo.zoom.serviceya;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
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

public class Buscador extends AppCompatActivity {

    private AutoCompleteTextView acQueBuscas;
    private Spinner spProvincia, spLocalidad, spGrupo, spCategoria;
    private CheckBox cbProvincia, cbLocalidad, cbGrupo, cbCategoria;
    private RecyclerView rvPresEncontrados;

    private List<ItemPrestador> presListSearch = new ArrayList<>();
    private BuscadorAdapter mAdapter;
    private ArrayList<Prestador> prestadores = new ArrayList<>();
    private ArrayList<String> provincias = new ArrayList<>();
    private ArrayList<String> localidades = new ArrayList<>();
    private ArrayList<String> grupos = new ArrayList<>();
    private ArrayList<String> categorias = new ArrayList<>();
    private int positionProvincia;
    private int positionLocalidad;
    private int positionGrupo;
    private int positionCategoria;

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


        mAdapter = new BuscadorAdapter(presListSearch);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPresEncontrados.setLayoutManager(mLayoutManager);
        rvPresEncontrados.setItemAnimator(new DefaultItemAnimator());
        rvPresEncontrados.setAdapter(mAdapter);

        rvPresEncontrados.addOnItemTouchListener(new RecyclerTouchListener(this,
                rvPresEncontrados, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(Buscador.this, "Single Click on position :"+position,
                        Toast.LENGTH_SHORT).show();
                ImageView picture=(ImageView)view.findViewById(R.id.imageView2);
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Buscador.this, "Single Click on Image :"+ position,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(Buscador.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

        buscarPrestadores(que_buscas, 10);
        buscarProvincias();


    }

    private void buscarPrestadores(String queBusco, int limite) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_PRESTADORES_SEARCH + queBusco + "&limite=" + limite, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void buscarProvincias() {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_PROVINCIAS, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void buscarLocalidades(String provincia) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_LOCALIDADES + provincia, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void buscarGrupos() {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_GRUPOS, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void buscarCategorias(String grupoName) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_CATEGORIAS + grupoName, //TODO:tengo que cambiar la URL en la Api.class y en el lado server PHP...
                null,
                Constants.CODE_GET_REQUEST);
        request.execute();

    }

    private void actualizarPrestadores(String provinciaName, String localidadName, String grupoName, String categoriaName, String queBusco) {

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_READ_PRESTADORES_SEARCH + provinciaName + "&local=" + localidadName +
                "&grupo=" + grupoName + "&categoria=" + categoriaName + "&que=" + queBusco,
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
                    case Api.URL_READ_PROVINCIAS:
                        JSONObject objectSitio = new JSONObject(s);
                        if (!objectSitio.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectSitio.getString("message") + ": provincias!", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            refreshProvincias(objectSitio.getJSONArray("provincias"));
                        }
                        break;
                    case Api.URL_READ_LOCALIDADES:
                        JSONObject objectLocalidad = new JSONObject(s);
                        if (!objectLocalidad.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectLocalidad.getString("message") + ": provincias!", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            refreshLocalidades(objectLocalidad.getJSONArray("localidades"));
                        }
                        break;
                    case Api.URL_READ_GRUPOS:
                        JSONObject objectGrupo = new JSONObject(s);
                        if (!objectGrupo.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectGrupo.getString("message") + ": provincias!", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            refreshGrupos(objectGrupo.getJSONArray("grupos"));
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
                    case Api.URL_READ_PRESTADORES_SEARCH:
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



    private void refreshProvincias(JSONArray provinciasJson) throws JSONException {

        provincias.clear();

        for (int i = 0; i < provinciasJson.length(); i++){
            JSONObject obj = provinciasJson.getJSONObject(i);
            provincias.add(obj.get("provincia").toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, provincias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincia.setAdapter(dataAdapter);

        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //((TextView) adapterView.getChildAt(0)).setTextSize(5);
                positionProvincia = i;
                buscarLocalidades(spProvincia.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void refreshLocalidades(JSONArray localidadesJson) throws JSONException {

        localidades.clear();

        for (int i = 0; i < localidadesJson.length(); i++){
            JSONObject obj = localidadesJson.getJSONObject(i);
            localidades.add(obj.get("localidad").toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, localidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalidad.setAdapter(dataAdapter);

        spLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //((TextView) adapterView.getChildAt(0)).setTextSize(5);
                positionLocalidad = i;
                buscarGrupos();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void refreshGrupos(JSONArray gruposJson) throws JSONException {

        grupos.clear();

        for (int i = 0; i < gruposJson.length(); i++){
            JSONObject obj = gruposJson.getJSONObject(i);
            grupos.add(obj.get("grupo").toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, provincias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupo.setAdapter(dataAdapter);

        spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //((TextView) adapterView.getChildAt(0)).setTextSize(5);
                positionGrupo = i;
                buscarCategorias(spGrupo.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //((TextView) adapterView.getChildAt(0)).setTextSize(5);
                actualizarPrestadores(
                        spProvincia.getItemAtPosition(positionProvincia).toString(),
                        spLocalidad.getItemAtPosition(positionLocalidad).toString(),
                        spGrupo.getItemAtPosition(positionGrupo).toString(),
                        spCategoria.getItemAtPosition(i).toString(),
                        acQueBuscas.getText().toString()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void refreshPresList(JSONArray prestadoresJson) throws JSONException {

        prestadores.clear();
        presListSearch.clear();

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

            presListSearch.add(new ItemPrestador(
                    obj.getString("imagen"),
                    obj.getString("nombre")+" "+obj.getString("apellido"),
                    "Calificación en proceso...",
                    "Comentarios en proceso..."
            ));
        }



        mAdapter.notifyDataSetChanged();

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
}

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
package com.domo.zoom.serviceya;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;

public class UserDataActual extends AppCompatActivity {

    EditText etName, etLastName, etEmail, etCelular, etPass;
    Button btActData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_actual);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the current intent
        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.
        final String action = intent.getStringExtra("key");

        if (!action.equals("registrarUser")){
            //if you want to send back data:
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result","No fue llamada correctamente");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
            //if you don't want to send back data:
//            Intent returnIntent = new Intent();
//            setResult(Activity.RESULT_CANCELED, returnIntent);
//            finish();
        }

        etName = (EditText) findViewById(R.id.etNombre);
        etLastName = (EditText) findViewById(R.id.etApellido);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etPass = findViewById(R.id.etPassword);
        btActData = (Button) findViewById(R.id.btActData);




        /*TODO: armar toda la Activity para registrar el Cliente final o para el login del Prestador.
        Primero pregunto si es cliente o prestador.
        Segundo muestro layout correspondiente.
        Cargo los datos en la DB.
        Regreso como resultado los datos del usuario registrado en la DB y también los cargo en preferencias.*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] colors = {"si", "no"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ud. es un usuario?");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                if (which > 0) { // es un Prestador
                    etName.setVisibility(View.GONE);
                    etLastName.setVisibility(View.GONE);
                    etEmail.setVisibility(View.VISIBLE);
                    etPass.setVisibility(View.VISIBLE);
                    etCelular.setVisibility(View.GONE);
                    btActData.setText("Acceso Prestador");

                    SharedPreferences.Editor editor = MainActivity.pref.edit();
                    editor.putBoolean(Constants.KEY_USER_FINAL, false); // Storing boolean - true/false
                    editor.apply(); // commit changes

                } else { // es un usuario final
                    etPass.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = MainActivity.pref.edit();
                    editor.putBoolean(Constants.KEY_USER_FINAL, true); // Storing boolean - true/false
                    editor.apply(); // commit changes
                }
                btActData.setEnabled(false);
            }
        });
        builder.show();

        btActData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isUserFinal() &&TextUtils.isEmpty(etName.getText())){
                    etName.setError("Por favor ingrese un nombre");
                    etName.requestFocus();
                    return;
                }
                if (!isUserFinal() &&TextUtils.isEmpty(etLastName.getText())){
                    etLastName.setError("Por favor ingrese un apellido");
                    etLastName.requestFocus();
                    return;
                }
                if (isUserFinal() && TextUtils.isEmpty(etCelular.getText())){
                    etCelular.setError("Por favor ingrese un celular");
                    etCelular.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(etEmail.getText())){
                    etEmail.setError("Por favor ingrese un email");
                    etEmail.requestFocus();
                    return;
                }
                if (!isUserFinal() && TextUtils.isEmpty(etPass.getText())){
                    etPass.setError("La clave es incorrecta");
                    etPass.requestFocus();
                    return;
                }
                if (isUserFinal()){
                    //TODO: cambiar URL para usar Api de Pronto Service correctamente.
                    PerformNetworkRequest request = new PerformNetworkRequest(
                            Api.URL_WRITE_ADD_USER + "'" + etEmail.getText().toString() +"'",
                            null,
                            Constants.CODE_GET_REQUEST);
                    request.execute();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result","Nuevo Usuario Final Cargado OK");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                } else {
                    PerformNetworkRequest request = new PerformNetworkRequest(
                            Api.URL_LOGIN_PRES + "'" + etEmail.getText().toString() +"'",
                            null,
                            Constants.CODE_GET_REQUEST);
                    request.execute();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result","Prestador login OK");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }


            }
        });
    }

    private boolean isUserFinal(){
        return MainActivity.pref.getBoolean(Constants.KEY_USER_FINAL, true);
    }

    public class Norm {
        String cadena;

        public Norm(String cadena) {
            this.cadena = cadena;
        }

        public String cleanString() {
            cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
            cadena = cadena.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            cadena = cadena.replaceAll("ñ","n");
            cadena = cadena.replaceAll("Ñ", "N");
            cadena = cadena.replaceAll("'", "");
            cadena = cadena.replaceAll("\"", "");
            cadena = cadena.replaceAll("&", "y");
            cadena = cadena.replaceAll("=", "");
            cadena = quitaEspacios(cadena);
            cadena = cadena.replaceAll(" ", "%20");
            return cadena;
        }

        public String quitaEspacios(String texto) {
            java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
            StringBuilder buff = new StringBuilder();
            while (tokens.hasMoreTokens()) {
                buff.append(" ").append(tokens.nextToken());
            }
            return buff.toString().trim();
        }
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
//            progressBar.setVisibility(View.VISIBLE);
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
//            progressBar.setVisibility(GONE);
            String [] urlmix = url.split("=");
            try {
                switch (urlmix[0]+"="+urlmix[1]+"="){
                    case Api.URL_READ_MODELOS_POR_MARCA_VEHI:
                        JSONObject objectHeroes = new JSONObject(s);
                        if (!objectHeroes.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectHeroes.getString("message") + ": modelos", Toast.LENGTH_SHORT).show();
                            //refreshing the herolist after every operation
                            //so we get an updated list
                            //we will create this method right now it is commented
                            //because we haven't created it yet

                            //refreshModelos(objectHeroes.getJSONArray("modelos"));
                        }
                        break;
                    case Api.URL_READ_MARCAS_POR_TIPO_VEHI:
//                    case Api.URL_READ_MARCAS_POR_TIPO_VEHI+2:
//                    case Api.URL_READ_MARCAS_POR_TIPO_VEHI+3:
//                    case Api.URL_READ_MARCAS_POR_TIPO_VEHI+5:
                        JSONObject objectMarcas = new JSONObject(s);
                        if (!objectMarcas.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectMarcas.getString("message") + ": marcas", Toast.LENGTH_SHORT).show();
                            //refreshMarcas(objectMarcas.getJSONArray("marcas"));
                        }
                        break;
                    case Api.URL_WRITE_ACT_USER +"&idUser=":
                    case Api.URL_WRITE_ADD_USER +"&idUser=":
                    case Api.URL_WRITE_ADD_PRO +"&idUser=":
                    case Api.URL_WRITE_ACT_PRO +"&idUser=":
                        JSONObject objectUser = new JSONObject(s);
                        if (!objectUser.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), objectUser.getString("message") + ": user", Toast.LENGTH_SHORT).show();
                            if (isUserFinal()){
                                //refreshUser(objectUser.getJSONArray("users"));
                            } else {
                                //refreshPro(objectUser.getJSONArray("users"));
                            }

                        }
                        break;
                    case Api.URL_READ_USER:
                    case Api.URL_READ_PRO:
                        JSONObject objectUserRead = new JSONObject(s);
                        if (!objectUserRead.getBoolean("error" ) && objectUserRead.getJSONArray("users").length() > 0) {

                            if (MainActivity.pref.getBoolean(Constants.KEY_USER_FINAL, true)){
                                Toast.makeText(getApplicationContext(), objectUserRead.getString("message") + ": email existente.", Toast.LENGTH_SHORT).show();
                            } else {
                                /*writeDataPro(0, 0, 0, 0, "1886",
                                        etName.getText().toString(), etLastName.getText().toString(),
                                        etEmail.getText().toString(), etCelular.getText().toString(),
                                        MainActivity.pref.getString(Constants.KEY_USER_FIREBASE_TOKEN, null),
                                        etPass.getText().toString());*/
                            }
                        } else {
                            if (MainActivity.pref.getBoolean(Constants.KEY_USER_FINAL, true)){
                                /*writeCreateUser(0, posTipoVe, posMarcaVe, posModeloVe, etDate.getText().toString(),
                                        etName.getText().toString(), etLastName.getText().toString(),
                                        etEmail.getText().toString(), etCelular.getText().toString(), MainActivity.pref.getString(Constants.KEY_USER_FIREBASE_TOKEN, null));*/
                            } else {
                                Toast.makeText(getApplicationContext(), objectUserRead.getString("message") + ": Sin autorización.", Toast.LENGTH_LONG).show();
                            }

                        }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                btActData.setEnabled(true);
            }
        });
        builder.show();

        btActData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserFinal() && TextUtils.isEmpty(etName.getText())){
                    etName.setError("Por favor ingrese un nombre");
                    etName.requestFocus();
                    return;
                }
                if (isUserFinal() && TextUtils.isEmpty(etLastName.getText())){
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
                            Api.URL_WRITE_ADD_USER +
                                    "&nombre="+new Norm(etName.getText().toString()).cleanString()+
                                    "&apellido="+new Norm(etLastName.getText().toString()).cleanString()+
                                    "&email="+new Norm(etEmail.getText().toString()).cleanString()+
                                    "&celular="+new Norm(etCelular.getText().toString()).cleanString(),
                            null,
                            Constants.CODE_GET_REQUEST);
                    request.execute();


                } else {
                    PerformNetworkRequest request = new PerformNetworkRequest(
                            Api.URL_LOGIN_PRES +
                                    "&emailPres="+new Norm(etEmail.getText().toString()).cleanString(),
                            null,
                            Constants.CODE_GET_REQUEST);
                    request.execute();

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
                    case Api.URL_WRITE_ADD_USER +"&nombre=":
                        JSONObject objectUser = new JSONObject(s);
                        if (!objectUser.getBoolean("error")&& objectUser.getJSONArray("nuevoUser").length() > 0) {
//                            Toast.makeText(getApplicationContext(), objectUser.getString("message") + ": user", Toast.LENGTH_SHORT).show();
                                //refreshUser(objectUser.getJSONArray("users"));

                            SharedPreferences.Editor editor = MainActivity.pref.edit();
                            editor.putBoolean(Constants.KEY_USER_EXISTE, true);
                            editor.putBoolean(Constants.KEY_USER_FINAL, true);
                            editor.putString(Constants.KEY_USER_ID,
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("id"));
                            editor.putString(Constants.KEY_USER_NOMBRE,
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("nombre") +" " +
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("apellido"));
                            editor.putString(Constants.KEY_USER_CELULAR,
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("celular"));
                            //editor.putString(Constants.KEY_USER_WEB,
                            //      objectUser.getJSONArray("nuevoUser").getJSONObject(0).getString("web"));
                            editor.putString(Constants.KEY_USER_EMAIL,
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("email"));
                            //editor.putString(Constants.KEY_USER_IMAGEN,
                            //      objectUser.getJSONArray("nuevoUser").getJSONObject(0).getString("imagen"));
                            editor.apply();

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("newUserId",
                                    objectUser.getJSONArray("nuevoUser").
                                            getJSONObject(0).getString("id")); //TODO: verificar nombre del parametro que viene de la DB
                            returnIntent.putExtra("result","Nuevo Usuario Final Cargado OK");
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        } else {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","Nuevo Usuario Final Cargado NOK");
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    break;
                    case Api.URL_LOGIN_PRES +"&emailPres=":
                        JSONObject objectLogPres = new JSONObject(s);
                        if (!objectLogPres.getBoolean("error" ) && objectLogPres.getJSONArray("loginPres").length() > 0) { //TODO: crear Api function y ver nombre de parametro
                            if (objectLogPres.getJSONArray("loginPres").
                                    getJSONObject(0).getString("password").
                                    equals(etPass.getText().toString())){
                                SharedPreferences.Editor editor = MainActivity.pref.edit();
                                editor.putBoolean(Constants.KEY_USER_EXISTE, true);
                                editor.putBoolean(Constants.KEY_USER_FINAL, false);
                                editor.putString(Constants.KEY_USER_ID,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("id"));
                                editor.putString(Constants.KEY_USER_NOMBRE,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("nombre") +" " +
                                                objectLogPres.getJSONArray("loginPres").
                                                        getJSONObject(0).getString("apellido"));
                                editor.putString(Constants.KEY_USER_CELULAR,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("celular"));
                                editor.putString(Constants.KEY_USER_WEB,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("web"));
                                editor.putString(Constants.KEY_USER_EMAIL,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("email"));
                                editor.putString(Constants.KEY_USER_IMAGEN,
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("imagen"));
                                editor.apply();

                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("presId",
                                        objectLogPres.getJSONArray("loginPres").
                                                getJSONObject(0).getString("id"));
                                returnIntent.putExtra("result", "Prestador login OK");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            } else {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "Prestador login NOK");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        }
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

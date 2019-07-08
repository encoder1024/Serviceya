package com.domo.zoom.serviceya;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.graphics.Typeface.BOLD;

public class PrestadorShow extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 113;
    TextView display;
    String action;
    String sentence;
    SpannableString s;
    private String califica = "8";
    final String prestador_id ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prestador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the current intent
        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.
        action = intent.getStringExtra("ACTION");
        final String prestador_id = String.valueOf(intent.getIntExtra("PRESTADOR_ID", 1));
        String prestador_nombre = intent.getStringExtra("PRESTADOR_NOMBRE");
        final String prestador_phone = intent.getStringExtra("PRESTADOR_PHONE");
        final String prestador_web = intent.getStringExtra("PRESTADOR_WEB");
        final String prestador_email = intent.getStringExtra("PRESTADOR_EMAIL");
        final String prestador_imagen = intent.getStringExtra("PRESTADOR_IMAGEN");
        final String prestador_servicios = intent.getStringExtra("PRESTADOR_SERVICIO");
        final String prestador_grupo = intent.getStringExtra("PRESTADOR_GRUPO");
        final String prestador_categoria = intent.getStringExtra("PRESTADOR_CATEGORIA");
        final String prestador_action = intent.getStringExtra("PRESTADOR_FROM");



        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(prestador_nombre);
        }

        if (prestador_action != null) {
            if (prestador_action.equals("Categoria")){
                PerformNetworkRequest request = new PerformNetworkRequest(
                        Api.URL_READ_SERVICE_CAT +
                                "&groupname=" + Uri.encode(prestador_grupo)+
                                "&categname=" + Uri.encode(prestador_categoria) +
                                "&presId=" + prestador_id,
                        null,
                        Constants.CODE_GET_REQUEST);
                request.execute();
            } else {
                PerformNetworkRequest request = new PerformNetworkRequest(
                        Api.URL_READ_SERVICE_BUS +
                                "&presGroupId=" + prestador_grupo +
                                "&presCategId=" + prestador_categoria +
                                "&presId=" + prestador_id,
                        null,
                        Constants.CODE_GET_REQUEST);
                request.execute();
            }
        }

        display = findViewById(R.id.tvShowPres);

        sentence = "Teléfono: " + prestador_phone + "\n\n";
        sentence = sentence + "Web: \n" + prestador_web + "\n\n";
        sentence = sentence + "Email: \n" + prestador_email + "\n\n";
        //sentence = sentence + "Servicios: \n" + prestador_servicios + "\n\n";
        //sentence = sentence + getResources().getString(R.string.large_text);

        s = new SpannableString(sentence);

        s.setSpan(new RelativeSizeSpan(1.3f), sentence.indexOf("Teléfono"), sentence.length(), 0);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), 0, 9, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        display.setText(s);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Estamos procesando la llamada...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (ContextCompat.checkSelfPermission( getApplicationContext(),
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    PerformNetworkRequest request = new PerformNetworkRequest(
                            Api.URL_WRITE_REG_CALL +
                                    "&userId=" + MainActivity.pref.getString(Constants.KEY_USER_ID, "1") +
                                    "&presId=" + prestador_id,
                            null,
                            Constants.CODE_GET_REQUEST);
                    request.execute();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ prestador_phone));//change the number
                    startActivity(callIntent);
                } else {
                    tengoPermisoLlamadas();
                }


            }
        });

        FloatingActionButton fabCali = findViewById(R.id.fabCalifica);
        fabCali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Estamos procesando la llamada...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (ContextCompat.checkSelfPermission( getApplicationContext(),
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {

                    AgeDialog();

//                    PerformNetworkRequest request = new PerformNetworkRequest(
//                            Api.URL_WRITE_REG_CALIF +
//                                    "&userId=" + MainActivity.pref.getString(Constants.KEY_USER_ID, "1") +
//                                    "&presId=" + prestador_id + "&califica=" + califica,
//                            null,
//                            Constants.CODE_GET_REQUEST);
//                    request.execute();
                } else {
                    tengoPermisoLlamadas();
                }


            }
        });
    }

    public void AgeDialog(){

        final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setCancelable(false);

        LinearLayout l1 = new LinearLayout(getApplicationContext());

        l1.setOrientation(LinearLayout.HORIZONTAL);
        final NumberPicker number =new NumberPicker((this));
        number.setMaxValue(10);
        number.setMinValue(1);
        number.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        number.setWrapSelectorWheel(true);
//        final NumberPicker ageUnitss = new NumberPicker(this);
//        final String arrays[] = new String[3];
//        arrays[0]="Years";
//        arrays[1]="Months";
//        arrays[2]="Days";

//        ageUnitss.setMaxValue(2);
//        ageUnitss.setMinValue(0);
//        ageUnitss.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        ageUnitss.setDisplayedValues(arrays);
//        ageUnitss.setWrapSelectorWheel(true);


        l1.addView(number);
//        l1.addView(ageUnitss);

        l1.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        alert.setView(l1);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                //ageUnits = arrays[(ageUnitss.getValue())];
                califica = String.valueOf(number.getValue());
                //fragment1.setAgeText(age +" "+ageUnits);

                PerformNetworkRequest request = new PerformNetworkRequest(
                        Api.URL_WRITE_REG_CALIF +
                                "&userId=" + MainActivity.pref.getString(Constants.KEY_USER_ID, "1") +
                                "&presId=" + prestador_id + "&califica=" + califica,
                        null,
                        Constants.CODE_GET_REQUEST);
                request.execute();
                Toast.makeText(getApplicationContext(), "Ud. ha calificado al prestador con un " + califica + ".", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private void tengoPermisoLlamadas() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
        if (isPrestador()){
            Toast.makeText(PrestadorShow.this,
                    "Gracias por elegirnos...", Toast.LENGTH_LONG)
                    .show();
            finish();
            moveTaskToBack(true);
            System.exit(0);
        } else {
            super.onBackPressed();
        }

    }

    private boolean isPrestador(){
        if (action != null) {
            if (action.equals("FORPRESTADOR")) return true;
            else {
                return false;
            }
        } else {
            return false;
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
        protected void onPostExecute(String st) {
            super.onPostExecute(st);
//            progressBar.setVisibility(GONE);
            String [] urlmix = url.split("=");
            try {
                switch (urlmix[0]+"="+urlmix[1]+"="){
                    case Api.URL_WRITE_REG_CALL +"&userId=":
                        JSONObject objectRegistro = new JSONObject(st);
                        if (!objectRegistro.getBoolean("error")&& objectRegistro.getJSONArray("registros").length() > 0) {
                            Toast.makeText(getApplicationContext(), objectRegistro.getString("message") + ": registros", Toast.LENGTH_SHORT).show();
//                            refreshUser(objectUser.getJSONArray("users"));
                        }

                    break;
                    case Api.URL_READ_SERVICE_CAT +"&groupname=":
                        JSONObject objectServCat = new JSONObject(st);
                        if (!objectServCat.getBoolean("error" ) && objectServCat.getJSONArray("servicios").length() > 0) { //TODO: crear Api function y ver nombre de parametro
                            sentence = sentence + "Servicios: \n" + objectServCat.getJSONArray("servicios").getJSONObject(0).getString("detalle") + "\n\n";

                            s = new SpannableString(sentence);

                            s.setSpan(new RelativeSizeSpan(1.3f), sentence.indexOf("Teléfono"), sentence.length(), 0);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), 0, 9, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Servicios:"), sentence.indexOf("Servicios:")+10, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Servicios:"), sentence.indexOf("Servicios:")+10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            display.setText(s);
                        }
                    break;
                    case Api.URL_READ_SERVICE_BUS +"&presGroupId=":
                        JSONObject objectServBus = new JSONObject(st);
                        if (!objectServBus.getBoolean("error" ) && objectServBus.getJSONArray("servicios").length() > 0) { //TODO: crear Api function y ver nombre de parametro
                            sentence = sentence + "Servicios: \n" + objectServBus.getJSONArray("servicios").getJSONObject(0).getString("detalle") + "\n\n";
                            s = new SpannableString(sentence);

                            s.setSpan(new RelativeSizeSpan(1.3f), sentence.indexOf("Teléfono"), sentence.length(), 0);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), 0, 9, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Web:"), sentence.indexOf("Web:")+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Email:"), sentence.indexOf("Email:")+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark, getTheme())), sentence.indexOf("Servicios:"), sentence.indexOf("Servicios:")+10, 0);
                            s.setSpan(new StyleSpan(Typeface.BOLD), sentence.indexOf("Servicios:"), sentence.indexOf("Servicios:")+10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            display.setText(s);
                        }
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

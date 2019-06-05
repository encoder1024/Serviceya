package com.domo.zoom.serviceya;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PrestadorShow extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 113;
    TextView display;

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
        final String prestador_id = intent.getStringExtra("PRESTADOR_ID");
        String prestador_nombre = intent.getStringExtra("PRESTADOR_NOMBRE");
        final String prestador_phone = intent.getStringExtra("PRESTADOR_PHONE");
        final String prestador_web = intent.getStringExtra("PRESTADOR_WEB");
        final String prestador_email = intent.getStringExtra("PRESTADOR_EMAIL");
        final String prestador_imagen = intent.getStringExtra("PRESTADOR_IMAGEN");
        final String prestador_servicios = intent.getStringExtra("PRESTADOR_SERVICIO");

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(prestador_nombre);
        }

        display = findViewById(R.id.tvShowPres);

        String sentence = "Teléfono: " + prestador_phone + "\n";
        sentence = sentence + "Web: " + prestador_web + "\n";
        sentence = sentence + "Email: " + prestador_email + "\n";
        sentence = sentence + "Servicios: " + prestador_servicios + "\n\n";
        sentence = sentence + getResources().getString(R.string.large_text);


        display.setText(sentence);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (ContextCompat.checkSelfPermission( getApplicationContext(),
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    //TODO: sendInteration(prestador_id, cliente_id);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ prestador_phone));//change the number
                    startActivity(callIntent);
                } else {
                    tengoPermisoLlamadas();
                }


            }
        });
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

}

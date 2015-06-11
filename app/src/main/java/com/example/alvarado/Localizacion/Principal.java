package com.example.alvarado.Localizacion;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Principal extends Activity {

    private static final android.R.attr R = ;
    private Button Actualizar, Desactivar,EstadoProveedor;
    private TextView Latitud, Longitud, Precision;

    private LocationManager locManager;
    private LocationListener locListener;


    @
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Actualizar = (Button) findViewById(R.id.Actualizar);
        Desactivar = (Button) findViewById(R.id.Desactivar);
        Latitud = (TextView) findViewById(R.id.PosLongitud);
        Longitud = (TextView) findViewById(R.id.PosLongitud);
        Precision = (TextView) findViewById(R.id.PosPrecision);
        EstadoProveedor = (TextView) findViewById(R.id.Estado);

        Actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });

        Desactivar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });

    }

    private void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
                lblEstadoProveedor.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                lblEstadoProveedor.setText("Provider ON ");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                lblEstadoProveedor.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            Latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            Longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            Precision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            Latitud.setText("Latitud: (sin_datos)");
            Longitud.setText("Longitud: (sin_datos)");
           Precision.setText("Precision: (sin_datos)");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
}

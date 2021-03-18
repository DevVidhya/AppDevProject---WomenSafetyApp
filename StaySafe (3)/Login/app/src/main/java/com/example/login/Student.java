package com.example.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Student extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    private Button Submit, Logout;
    private Button Maps;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;


    private Button Siren, Call;


    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long MIN_TIME = 1000000;
    private final long MIN_DIST = 5;
    private LatLng LatLng;


    private String placeType = "police";
    private String placeName = "Police Station";

    SharedPreferences prf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        Submit = (Button) findViewById(R.id.bt1);
        Logout = (Button) findViewById(R.id.log);
        Siren = (Button) findViewById(R.id.Panic);
        Call = (Button) findViewById(R.id.call);
        Maps = (Button) findViewById(R.id.tv5);


        prf = getSharedPreferences("user_details", MODE_PRIVATE);

       ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sendSMSMessage();
               String Message = "Send Help !!! \nLocation:\nLatitude: " + LatLng.latitude +"\nLongitude: "+
                        LatLng.longitude + "\nGoogle Maps Link:\n https://www.google.com/maps/@"+LatLng.latitude+","+
                        LatLng.longitude+",15z" +"\nPerson ID: " + prf.getString("mail",null);
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendMultipartTextMessage("+917737252576",null,smsManager.divideMessage(Message),null,null);


                Toast.makeText(getApplicationContext(), "SMS sent!!",
                        Toast.LENGTH_LONG).show();

            }
        });

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Student.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Siren.getText().toString().equals("Alert")) {
                    Siren.setText("Stop");
                    startService(new Intent(Student.this, MyService.class));
                } else {
                    Siren.setText("Alert");
                    stopService(new Intent(Student.this, MyService.class));
                }
            }
        });

        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/police/@" + LatLng.latitude + "," +
                        LatLng.longitude + ",15z/data=!3m1!4b1"));  //https://www.google.co.in/maps/
                startActivity(intent);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void callPhoneNumber() {
        try {


            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Student.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: 100"));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: 100"));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    LatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mapAPI.addMarker(new MarkerOptions().position(LatLng).title("Current Location"));
                    mapAPI.moveCamera((CameraUpdateFactory.newLatLng(LatLng)));
                    mapAPI.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(LatLng.latitude, LatLng.longitude), 10
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}



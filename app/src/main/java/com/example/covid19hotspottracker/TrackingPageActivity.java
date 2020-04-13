package com.example.covid19hotspottracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.covid19hotspottracker.network.NetworkUtil;
import com.example.covid19hotspottracker.network.pojo.AffectedAreaDataResponsePojo;
import com.example.covid19hotspottracker.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.ArrayList;

public class TrackingPageActivity extends AppCompatActivity implements NetworkUtil.AffectedAreaInterface{
    FusedLocationProviderClient mFusedLocationClient;
    private SparseIntArray sparseIntArray=new SparseIntArray();
    private ArrayList<AffectedAreaDataResponsePojo> affectedAreaDataResponsePojoArrayList=new ArrayList<>();
    int PERMISSION_ID = 9;
    private ImageView indicator_image_view;
    Location currentLocation=new Location("");
    private TextView indicator_title_text_view;
    private TextView indicator_desc_text_view;
    private Button btn_stop_tracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);
        indicator_image_view=findViewById(R.id.indicator_image_view);
        btn_stop_tracking=findViewById(R.id.btn_stop_tracking);

        btn_stop_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                Intent intent=new Intent(TrackingPageActivity.this,HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        indicator_image_view=findViewById(R.id.indicator_image_view);
        indicator_title_text_view=findViewById(R.id.indicator_title_text_view);
        indicator_desc_text_view=findViewById(R.id.indicator_desc_text_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

//        mFusedLocationClient.requestLocationUpdates()

        RequestQueue queue = Volley.newRequestQueue(this);

        //Network call to get cities

        NetworkUtil networkUtil=new NetworkUtil(this);
        queue.add(networkUtil.getAffectedAreas(this));



    }
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
//                                    System.out.println("Distance is "+targetLocation.distanceTo(location));
//                                    Float l2=targetLocation.distanceTo(location);
//                                    if((l2)<=3000f){
//
////                                        Toast.makeText(TrackingPageActivity.this,"This is it",Toast.LENGTH_LONG).show();
//                                    }
                                    currentLocation=location;
                                    System.out.println("Latitude is"+location.getLatitude());
                                    System.out.println("Longitude is"+location.getLongitude());
                                    checkLevelOfArea();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            currentLocation=mLastLocation;
            checkLevelOfArea();
            System.out.println("Latitude is"+mLastLocation.getLatitude());
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    @Override
    public void onAffectedAreaFetched(JSONObject jsonObject) {

        affectedAreaDataResponsePojoArrayList.addAll(Utils.getAffectedAreaList(jsonObject));
        checkLevelOfArea();
        System.out.println("longitude is "+affectedAreaDataResponsePojoArrayList.get(0).getLongitude());
    }
    private void checkLevelOfArea(){

        if(currentLocation!=null&&currentLocation.getLongitude()!=0&&affectedAreaDataResponsePojoArrayList!=null
                && affectedAreaDataResponsePojoArrayList.size()!=0){

            Location affectedLocation=new Location("");
            for (int i=0;i<affectedAreaDataResponsePojoArrayList.size();i++){

                affectedLocation.setLatitude(Double.parseDouble(affectedAreaDataResponsePojoArrayList.get(i).getLat()));
                affectedLocation.setLongitude(Double.parseDouble(affectedAreaDataResponsePojoArrayList.get(i).getLongitude()));
                Float l2=affectedLocation.distanceTo(currentLocation);
                                    if((l2)<=2000f){

                                        indicator_image_view.setColorFilter(ContextCompat.getColor(TrackingPageActivity.this, R.color.red));

                                        indicator_title_text_view.setText(getResources().getString(R.string.red_zone));
                                        indicator_desc_text_view.setTextColor(getResources().getColor(R.color.red));
                                        indicator_desc_text_view.setText(getResources().getString(R.string.indicator_red_desc));
                                        indicator_title_text_view.setTextColor(getResources().getColor(R.color.red));


                                    }


            }


        }


    }
}

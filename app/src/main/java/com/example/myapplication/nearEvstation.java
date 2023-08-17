package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.HomeAdapter.FeaturedAdapater;
import com.example.myapplication.HomeAdapter.FeaturedHelperClass;
import com.example.myapplication.HomeAdapter.FeaturedNearMe;
import com.example.myapplication.HomeAdapter.nearHelperClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityNearEvstationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class nearEvstation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat, lng;

    private static final int Request_code = 101;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityNearEvstationBinding binding;
    RecyclerView nearMe_Rec;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        binding = ActivityNearEvstationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //hooks
        nearMe_Rec=findViewById(R.id.nearMe_recycler4);
        locationNearBY();
    }

    private void locationNearBY() {
        nearMe_Rec.setHasFixedSize(true);
        nearMe_Rec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<nearHelperClass> featuredLocations1=new ArrayList<>();

        featuredLocations1.add(new nearHelperClass(R.drawable.station_view7,"Mansingh eStation","59, SrinagarColony, L.B.Nagar, Hyderabad","Connector:19KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view5,"TATA ev Station","Immamguda, Rangareddy SH-5, Hyderabad","Connector:35KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view6,"Indian ev Station","10-C,Goshamahal, Nampally, Hyderabad","Connector:10KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view3,"Tesla ev Station","CFVX+86X, West Marredpally, Secunderabad","Connector:25KW"));

        adapter=new FeaturedNearMe(featuredLocations1);
        nearMe_Rec.setAdapter(adapter);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getCurrentLocation();
        mMap.setMyLocationEnabled(true);
    }
    private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;
        }
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationRequest==null){
                    Toast.makeText(getApplicationContext(), "Current location is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location:locationResult.getLocations()){
                    if(location!=null){

                    }
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){
                    lat=location.getLatitude();
                    lng=location.getLongitude();
                    LatLng latLng=new LatLng(lat,lng);
                    LatLng mansingev=new LatLng(17.33591173252582, 78.55087280377514);
                    LatLng TATAev=new LatLng(17.327176487736757, 78.64855901563469);
                    LatLng Indianev=new LatLng(17.29083731718109, 78.47250094159425);
                    LatLng Teslaev=new LatLng(17.208532726428505, 78.49263642231905);
                    LatLng Fortumev=new LatLng(17.1911569286413, 78.47387423261938);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
                    mMap.addMarker(new MarkerOptions().position(mansingev).title("Mansingh E Station"));
                    mMap.addMarker(new MarkerOptions().position(TATAev).title("TATA EV Station"));
                    mMap.addMarker(new MarkerOptions().position(Indianev).title("INDIAN EV Station"));
                    mMap.addMarker(new MarkerOptions().position(Teslaev).title("TESLA EV Station"));
                    mMap.addMarker(new MarkerOptions().position(Fortumev).title("Fortum EV Station"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
                }
            }
        });
    }
}
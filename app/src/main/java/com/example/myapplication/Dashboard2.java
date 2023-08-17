package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.HomeAdapter.BookingsAdapter;
import com.example.myapplication.HomeAdapter.BookingsHelper;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Dashboard2 extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double lat, lng;
    private static final int Request_code = 101;
    private GoogleMap mMap;
    NavigationView navigationView;
    Button btn,nearby1,nearby4;
    Button nearby2,nearby3;
    TextView fname,usernameTextView,emailTextView;;
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar tool_bar;
    RecyclerView featuredRecycler,nearbyRecycler,bookings;
    RecyclerView.Adapter adapter;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard2);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        btn=findViewById(R.id.map_id);
        nearby1=findViewById(R.id.electric_1);
        nearby2=findViewById(R.id.petrol_5);
        nearby3=findViewById(R.id.restaurants_3);
        nearby4=findViewById(R.id.atm_4);

        fname=findViewById(R.id.full_name);
        tool_bar=findViewById(R.id.toolbar4);
        usernameTextView = headerView.findViewById(R.id.username3);
        emailTextView = headerView.findViewById(R.id.usermail);

        //nearby places button click
        nearby1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard2.this,nearEvstation.class);
                startActivity(intent);
            }
        });
        nearby2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard2.this,nearbypetrol.class);
                startActivity(intent);
            }
        });
        nearby3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard2.this,neareating.class);
                startActivity(intent);
            }
        });
        nearby4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard2.this,nearATM.class);
                startActivity(intent);
            }
        });

        //hooks
        featuredRecycler=findViewById(R.id.featured_recycler);
        featuredRecycler();
        nearbyRecycler=findViewById(R.id.featured_recycler2);
        nearbyRecycler();
        bookings=findViewById(R.id.featured_recycle3);
        bookings();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard2.this,MapsView.class);
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,tool_bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        showtheData();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void showtheData() {
        Intent intent=getIntent();
        String User_name=intent.getStringExtra("uname");
        String email=intent.getStringExtra("mail");
        fname.setText(User_name);
        usernameTextView.setText(User_name);
        emailTextView.setText(email);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
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
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
            }
        });
    }
    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations=new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.station_view1,"Fortum ev Station","Immamguda, Rangareddy SH-5, Hyderabad","Connector:25KW"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.station_view2,"Tata ev Station","10-C,Goshamahal, Nampally, Hyderabad","Connector:15KW"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.station_view3,"Tesla ev Station","CFVX+86X, West Marredpally, Secunderabad","Connector:30KW"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.stationview4,"Drive ev Station","59, SrinagarColony, L.B.Nagar, Hyderabad","Connector:18KW"));
        adapter=new FeaturedAdapater(featuredLocations);
        featuredRecycler.setAdapter(adapter);

    }
    private void nearbyRecycler() {
        nearbyRecycler.setHasFixedSize(true);
        nearbyRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<nearHelperClass> featuredLocations1=new ArrayList<>();

        featuredLocations1.add(new nearHelperClass(R.drawable.station_view7,"Mansingh eStation","59, SrinagarColony, L.B.Nagar, Hyderabad","Connector:19KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view5,"TATA ev Station","Immamguda, Rangareddy SH-5, Hyderabad","Connector:35KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view6,"Indian ev Station","10-C,Goshamahal, Nampally, Hyderabad","Connector:10KW"));
        featuredLocations1.add(new nearHelperClass(R.drawable.station_view3,"Tesla ev Station","CFVX+86X, West Marredpally, Secunderabad","Connector:25KW"));

        adapter=new FeaturedNearMe(featuredLocations1);
        nearbyRecycler.setAdapter(adapter);

    }
    private void bookings() {
        bookings.setHasFixedSize(true);
        bookings.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<BookingsHelper> featuredLocations2=new ArrayList<>();
        featuredLocations2.add(new BookingsHelper(R.drawable.ola_bike,"OLA ev Scooter","Status:charging","Connector:19KW","Location: 59, SrinagarColony, L.B.Nagar, Hyderabad"));
        featuredLocations2.add(new BookingsHelper(R.drawable.nexon_ele,"NEXON electic","Status:Completed","Connector:35KW","Location:Immamguda, Rangareddy SH-5, Hyderabad"));
        adapter=new BookingsAdapter(featuredLocations2);
        bookings.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                // Launch login activity
                Toast.makeText(this, "Successful Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard2.this, Login_page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
                break;
            case R.id.nav_home:
                break;
            default:
                // Handle other menu items
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
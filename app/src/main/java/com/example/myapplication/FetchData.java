package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class FetchData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap googleMap;
    String url;
    @Override
    protected void onPostExecute(String result) {
       try{
           JSONObject jsonObject=new JSONObject(result);
           JSONArray jsonArray=jsonObject.getJSONArray("results");

           for(int i=0;i<jsonArray.length();i++){
               JSONObject jsonObject1=jsonArray.getJSONObject(i);
               JSONObject getLocation=jsonObject1.getJSONObject("geometry")
                       .getJSONObject("location");
               String lat=getLocation.getString("lat");
               String lng=getLocation.getString("lng");
               JSONObject getName=jsonArray.getJSONObject(i);
               String name=getName.getString("name");
               LatLng latLng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
               MarkerOptions markerOptions=new MarkerOptions();
               markerOptions.title(name);
               markerOptions.position(latLng);
               googleMap.addMarker(markerOptions);
               googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            googleMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }




        }

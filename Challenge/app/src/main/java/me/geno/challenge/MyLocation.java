package me.geno.challenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Neel on 10/5/2015.
 */
public class MyLocation extends Fragment {
    public View view;
    private GoogleMap mMap;
    LatLng myLocation;
    HomeActivity location = new HomeActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_location, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mMap =  mapFragment.getMap();
        if(location.latitude!=0 && location.longitude!=0)
            myLocation = new LatLng(location.latitude, location.longitude);
        else
            myLocation = new LatLng(40.7431735,-73.9799391);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("I am here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        return view;
    }
}

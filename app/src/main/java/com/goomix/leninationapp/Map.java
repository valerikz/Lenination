package com.goomix.leninationapp;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.goomix.leninationapp.model.Statue;

import java.util.ArrayList;
import java.util.List;


public class Map extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap statueMap;

    public Map() {
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (statueMap == null) {
            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        statueMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {

        statueMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        statueMap.getUiSettings().setMapToolbarEnabled(true);

        List<Statue> statues = ((MainActivity)getActivity()).getStatues();
        List<Marker> markers = new ArrayList<>();
        for (Statue statue: statues) {
            Marker marker = addMarker(statue);
            markers.add(marker);
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        statueMap.moveCamera(cu);
        
        //statueMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
    }

    private Marker addMarker(Statue statue) {
        String[] coordinates = statue.getCoordinates().split(",");
        LatLng position = new LatLng(Double.valueOf(coordinates[0]), Double.valueOf(coordinates[1]));
        return statueMap.addMarker(new MarkerOptions().position(position).title(statue.getTitle()));
    }
}


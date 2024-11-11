package com.example.previsaodotempo.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.Fragment;

import com.example.previsaodotempo.R;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private double latitude = -23.5505;
    private double longitude = -46.6333;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapView mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (getArguments() != null) {
            latitude = getArguments().getDouble("latitude", latitude);
            longitude = getArguments().getDouble("longitude", longitude);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        LatLng cityLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(cityLocation).title("Cidade atual"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 12));
    }

    public static MapFragment with(String cityName, Context context) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        Address address = null;

        try {
            address = new Geocoder(context).getFromLocationName(cityName, 1).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        args.putDouble("latitude", address.getLatitude());

        args.putDouble("longitude", address.getLongitude());

        fragment.setArguments(args);

        return fragment;
    }
}


package com.example.patrick.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.cluster.MyItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by Patrizio Perna on 21/01/15.
 */
public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private String[] startFrom;
    private static View rootView;
    private ClusterManager<MyItem> mClusterManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
            return rootView;
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is  */
        }


        // Recupero punto di partenza da file config.xml
        startFrom = getResources().getStringArray(R.array.startFrom);

        // Recupero riferimento al fragment per la mappa
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

     return rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.i("myMapFragment","onDestroyView");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);

        LatLng latLngStart = new LatLng(Float.parseFloat(startFrom[0]),Float.parseFloat(startFrom[1]));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngStart,Float.parseFloat(startFrom[2])));

        setUpCluster(googleMap);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("myMapFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("myMapFragment","onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("myMapFragment","onResume");
    }

    private void setUpCluster(GoogleMap googleMap){

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getActivity(),googleMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraChangeListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();

    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = Float.parseFloat(startFrom[0]);
        double lng =  Float.parseFloat(startFrom[1]);

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            LatLng latlng = new LatLng(lat, lng);
            MyItem offsetItem = new MyItem(latlng);
            mClusterManager.addItem(offsetItem);
        }
    }
}

package com.example.patrick.myapplication.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.NodeBean;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Patrizio Perna on 19/01/15.
 */
public class MyDetailFragment extends Fragment implements OnMapReadyCallback {
    /**
     * Chiave per l'argomento che viene passato.
     */
    public static final String ARGUMENT_ITEM        = "ITEM";
    public static final String ARGUMENT_ITEM_LAYER  = "LAYER";
    private MapFragment mapFragment;
    private NodeBean node;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Creo la view per questo fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Recupero riferimento al fragment per la mappa
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.mapFragmentDetail);
        mapFragment.getMapAsync(this);


        // Recupero oggetto NodeBean cliccato
        Bundle arguments = getArguments();
        node = arguments.getParcelable("com.example.patrick.myapplication.NodeBean");

        //textView.setText("Item selected: "+arguments.getString(ARGUMENT_ITEM));
        TextView textView1 =(TextView)rootView.findViewById(R.id.detailsText);
        textView1.setText(node.getUser());

        // Test oggetto recuperato
        Log.i("myDetailFragment",node.getUser());



        return rootView;
    }

    public void setText(String item) {
        TextView view = (TextView) getView().findViewById(R.id.detailsText);
        view.setText(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String tmp[] = node.getGeometry().getCoordinates();
        String lon = tmp[0];
        String lat = tmp[1];

        LatLng nodeCoordinates = new LatLng(Float.parseFloat(lat),Float.parseFloat(lon));

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nodeCoordinates, 12));

        googleMap.addMarker(new MarkerOptions()
                .title(node.getName())
                .snippet(node.getDescription())
                .position(nodeCoordinates));
    }
}

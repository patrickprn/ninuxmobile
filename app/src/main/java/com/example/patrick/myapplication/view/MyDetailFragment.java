package com.example.patrick.myapplication.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.NodeBean;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

        ((ImageButton) rootView.findViewById(R.id.skypeButtonChat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skypeName = "marco_trib4";
                if (skypeName.length() <= 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter skype username to message", Toast.LENGTH_SHORT).show();
                } else {
                    String mySkypeUri = "skype:" + skypeName + "?chat";
                    SkypeUri(getActivity(), mySkypeUri);
                }
            }
        });

        ((ImageButton) rootView.findViewById(R.id.skypeButtonCall)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skypeName = "marco_trib4";
                if (skypeName.length() <= 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter skype username to message", Toast.LENGTH_SHORT).show();
                } else {
                    String mySkypeUri = "skype:"+skypeName+"?call";
                    SkypeUri(getActivity(), mySkypeUri);
                }
            }
        });

        ((ImageButton) rootView.findViewById(R.id.telegramButtonChat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/stickers"));
                startActivity(browserIntent);
            }
        });
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

        int resource;
        if(node.isActive())
            resource = R.drawable.attivo;
        else
            resource = R.drawable.potenziale;

        googleMap.addMarker(new MarkerOptions()
                .title(node.getName())
                .snippet(node.getDescription())
                .position(nodeCoordinates))
                .setIcon(BitmapDescriptorFactory.fromResource(resource));
    }

    public void SkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            return;
        }
        Uri skypeUri = Uri.parse(mySkypeUri);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }

    /**
     * Determine whether the Skype for Android client is installed on this device.
     */
    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    /**
     * Install the Skype client through the market: URI scheme.
     */

    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);
        return;
    }
}

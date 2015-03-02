package com.example.patrick.myapplication.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.NodeBean;
import com.example.patrick.myapplication.view.MyDetailFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Patrizio Perna on 19/01/15.
 */
public class DetailActivity extends FragmentActivity {

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Crea gli argomenti da passare al fragment in base all'intent di chiamata.
        Bundle arguments = getIntent().getExtras();

        // Crea il fragment.
        MyDetailFragment myDetailFragment = new MyDetailFragment();
        myDetailFragment.setArguments(arguments);

        // Visualizza il fragment.
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, myDetailFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
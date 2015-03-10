package com.example.patrick.myapplication.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.Geometry;
import com.example.patrick.myapplication.bean.NodeBean;
import com.example.patrick.myapplication.cluster.MyClusterRenderer;
import com.example.patrick.myapplication.network.ServerProxy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Patrizio Perna on 21/01/15.
 */
public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private String[] startFrom;
    private static View rootView;
    private ClusterManager<NodeBean> mClusterManager;
    private ArrayList<NodeBean> arrayNode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        Log.i("myMapFragment","onCreateView");

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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("myMapFragment","onActivityCreated");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("myMapFragment","onViewCreated");
        GetNodeListTask getNodeListTask = new GetNodeListTask();
        getNodeListTask.execute();
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
        mClusterManager = new ClusterManager<NodeBean>(getActivity(),googleMap);

        // Setting the custom render
        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(),googleMap, mClusterManager));

        // Point the map's listeners at the listeners implemented by the cluster manager.
        googleMap.setOnCameraChangeListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<NodeBean>() {
            @Override
            public boolean onClusterItemClick(NodeBean nodeBean) {
                return false;
            }
        });
    }

    /**
     * Task per recuperare la lista completa dei nodi
     */

    // creo una classe innestata per reperire la lista di nodi
    private class GetNodeListTask extends
            AsyncTask<Void, Void, ArrayList<NodeBean>> {
        private PowerManager.WakeLock mWakeLock;

        private String msg;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager powerManager = (PowerManager) getActivity().getSystemService(getActivity().POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "MainActivity");
            mWakeLock.acquire();

            Toast.makeText(getActivity(), "Comunicazione con il server in corso...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<NodeBean> doInBackground(Void...voids) {
            ServerProxy proxy = new ServerProxy();
            try {
                Log.i("doInBackground",
                        "Comunicazione con il server in corso...");
                return proxy.getNodesList("","roma","900");
            } catch (IOException e) {
                Log.i("doInBackground",
                        "Errore nella comunicazione con il server in doBackground",
                        e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NodeBean> result) {
            super.onPostExecute(result);

            Toast.makeText(getActivity(), "Dati scaricati", Toast.LENGTH_SHORT).show();
            try{
                for (NodeBean node :result){
                    mClusterManager.addItem(node);
                }
            }
            catch(Exception e){
                Toast.makeText(getActivity(),"Problem reading list of makers",Toast.LENGTH_SHORT).show();
            }
        }
    }




}

package com.example.patrick.myapplication.cluster;

import android.content.Context;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.NodeBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Patrizio Perna on 10/03/15.
 */
public class MyClusterRenderer extends DefaultClusterRenderer<NodeBean> {
    public MyClusterRenderer(Context context, GoogleMap map, ClusterManager<NodeBean> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onClusterItemRendered(NodeBean clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        // access to marker
    }

    @Override
    protected void onBeforeClusterItemRendered(NodeBean item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        int resource;
        markerOptions.title(item.getName());
        if(item.isActive())
            resource = R.drawable.attivo;
        else
            resource = R.drawable.potenziale;

        markerOptions.icon(BitmapDescriptorFactory.fromResource(resource));
    }
}

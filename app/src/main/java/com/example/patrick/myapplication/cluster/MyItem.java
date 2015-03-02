package com.example.patrick.myapplication.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**Class that implements ClusterItem
 * Created by Patrizio Perna on 02/03/15.
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;

    public MyItem(LatLng mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}

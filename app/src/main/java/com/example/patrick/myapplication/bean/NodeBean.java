
package com.example.patrick.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NodeBean implements Parcelable {
    private String added;
    private String address;
    private String description;
    private String details;
    private String elev;
    private Geometry geometry;
    private String layer;
    private String layer_name;
    private String name;
    private String slug;
    private String status;
    private String updated;
    private String user;


    private NodeBean() {
    }

    private NodeBean(Parcel in) {
        readFromParcel(in);
    }

    public String getAdded() {
        return this.added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getElev() {
        return this.elev;
    }

    public void setElev(String elev) {
        this.elev = elev;
    }

    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getLayer() {
        return this.layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getLayer_name() {
        return this.layer_name;
    }

    public void setLayer_name(String layer_name) {
        this.layer_name = layer_name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String toString() {
        return slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(added);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(details);

        dest.writeString(elev);

        dest.writeParcelable(geometry, flags);
        dest.writeString(layer);
        dest.writeString(layer_name);
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeString(status);
        dest.writeString(updated);
        dest.writeString(user);
    }

    /**
     * Called from the constructor to create this object from a parcel.
     *
     * @param in parcel from which to re-create object
     */
    private void readFromParcel(Parcel in) {

        added = in.readString();
        address = in.readString();
        description = in.readString();
        details = in.readString();
        elev = in.readString();
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        layer = in.readString();
        layer_name = in.readString();
        name = in.readString();
        slug = in.readString();
        status = in.readString();
        updated = in.readString();
        user = in.readString();
    }

    public static final Parcelable.Creator<NodeBean> CREATOR
            = new Parcelable.Creator<NodeBean>() {
        public NodeBean createFromParcel(Parcel in) {
            return new NodeBean(in);
        }

        public NodeBean[] newArray(int size) {
            return new NodeBean[size];
        }
    };

}

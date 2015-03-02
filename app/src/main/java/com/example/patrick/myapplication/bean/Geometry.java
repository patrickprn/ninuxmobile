
package com.example.patrick.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Geometry implements Parcelable{

    private String[] coordinates;
   	private String type;

    public Geometry(String[] coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }
    public Geometry(Parcel in){
        readFromParcel(in);
    }

    public String[] getCoordinates(){
		return this.coordinates;
	}
	public void setCoordinates(String[] coordinates){
		this.coordinates = coordinates;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(coordinates);
        dest.writeString(type);
    }

    private void readFromParcel(Parcel in) {
        coordinates = in.createStringArray();
        type = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Geometry createFromParcel(Parcel in) { return new Geometry( in); }
        public Geometry[] newArray(int size) { return new Geometry[size]; }
    };

}

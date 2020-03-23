package com.example.fotagclcyau;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class ImageObject implements Parcelable, Comparable {

    private String fileLoc;
    private float rating;

    public ImageObject(Parcel in){
        this(in.readString(), in.readFloat());
    }

    public ImageObject(String fileLoc, int rating){
        this(fileLoc, (float) rating);
    }

    public ImageObject(String fileLoc, float rating){
        this.fileLoc = fileLoc;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFileLoc() {
        return fileLoc;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getFileLoc());
        parcel.writeFloat(getRating());
    }

    public static final Parcelable.Creator<ImageObject> CREATOR =
            new Parcelable.Creator<ImageObject>(){

                @Override
                public ImageObject createFromParcel(Parcel parcel) {
                    return new ImageObject(parcel);
                }

                @Override
                public ImageObject[] newArray(int i) {
                    return new ImageObject[0];
                }
            };

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}

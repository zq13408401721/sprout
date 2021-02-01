package com.sprout.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Img implements Parcelable {

    private String path;
    private List<Tag> tags;

    protected Img(Parcel in) {
        path = in.readString();
    }

    public static final Creator<Img> CREATOR = new Creator<Img>() {
        @Override
        public Img createFromParcel(Parcel in) {
            return new Img(in);
        }

        @Override
        public Img[] newArray(int size) {
            return new Img[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
    }

    class Tag{
        private float x;
        private float y;
        private int type;
        private String name;
        private double lng;
        private double lat;
    }
}

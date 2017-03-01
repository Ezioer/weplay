package com.example.weile.materialdesignexa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlData implements Parcelable {
    public boolean error;
    public ArrayList<Results> results;
    @Override
    public int describeContents() {
        return 0;
    }
    public static class Results implements Parcelable{
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        @Override
        public int describeContents() {
            return 0;
        }

        protected Results(Parcel in){
            this._id=in.readString();
            this.createdAt=in.readString();
            this.desc=in.readString();
            this.publishedAt=in.readString();
            this.source=in.readString();
            this.type=in.readString();
            this.url=in.readString();
            this.who=in.readString();
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this._id);
            parcel.writeString(this.createdAt);
            parcel.writeString(this.desc);
            parcel.writeString(this.publishedAt);
            parcel.writeString(this.source);
            parcel.writeString(this.type);
            parcel.writeString(this.url);
            parcel.writeString(this.who);
        }
        public static final Parcelable.Creator<Results> CREATOR = new Parcelable
                .Creator<Results>() {
            @Override
            public Results createFromParcel(Parcel source) {
                return new Results(source);
            }

            @Override
            public Results[] newArray(int size) {
                return new Results[size];
            }
        };
    }
    protected GirlData(Parcel in){
        this.results=in.createTypedArrayList(Results.CREATOR);
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeTypedList(results);
    }
    public static final Parcelable.Creator<GirlData> CREATOR = new Parcelable
            .Creator<GirlData>() {
        @Override
        public GirlData createFromParcel(Parcel source) {
            return new GirlData(source);
        }

        @Override
        public GirlData[] newArray(int size) {
            return new GirlData[size];
        }
    };
}

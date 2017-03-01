package com.example.weile.materialdesignexa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by weile on 2017/2/27.
 */
public class AdBean implements Parcelable {

    public ArrayList<Banner> banner_list;
    @Override
    public int describeContents() {
        return 0;
    }

    public static class Banner implements Parcelable{

        public int id;
        public String img;
        public String tag;
        public String tag_color;
        @Override
        public int describeContents() {
            return 0;
        }
        protected Banner (Parcel in){
            this.id=in.readInt();
            this.img=in.readString();
            this.tag=in.readString();
            this.tag_color=in.readString();
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeString(this.img);
            parcel.writeString(this.tag);
            parcel.writeString(this.tag_color);

        }
        public static final Parcelable.Creator<Banner> CREATOR = new Parcelable.Creator<Banner>() {
            @Override
            public Banner createFromParcel(Parcel source) {
                return new Banner(source);
            }

            @Override
            public Banner[] newArray(int size) {
                return new Banner[size];
            }
        };
    }
    protected AdBean(Parcel in){
        this.banner_list=in.createTypedArrayList(Banner.CREATOR);
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(banner_list);
    }
    public static final Parcelable.Creator<AdBean> CREATOR = new Parcelable.Creator<AdBean>() {
        @Override
        public AdBean createFromParcel(Parcel source) {
            return new AdBean(source);
        }

        @Override
        public AdBean[] newArray(int size) {
            return new AdBean[size];
        }
    };
}

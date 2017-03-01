package com.example.weile.materialdesignexa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentDetailBean implements Parcelable {
    public int display_style;
    public String short_url;
    @SerializedName("abstract")
    public String abs;
    public int app_css;
    public int like_count;
    public ArrayList<Thumbs> thumbs;
    public String create_time;
    public int id;
    public boolean is_editor_choice;
    public String original_url;
    public String content;
    public String share_pic_url;
    public String type;
    public boolean isliked;
    public ArrayList<Photos> photos;
    public String public_time;
    public String url;
    public Authors author;
    public String column;
    public int comments_count;
    public String title;
    protected DoubanMomentDetailBean(Parcel in) {
        this.display_style=in.readInt();
        this.short_url=in.readString();
        this.abs=in.readString();
        this.app_css=in.readInt();
        this.like_count=in.readInt();
        this.thumbs=in.createTypedArrayList(Thumbs.CREATOR);
        this.create_time=in.readString();
        this.id=in.readInt();
        this.original_url=in.readString();
        this.content=in.readString();
        this.share_pic_url=in.readString();
        this.type=in.readString();
        this.photos=in.createTypedArrayList(Photos.CREATOR);
        this.public_time=in.readString();
        this.url=in.readString();
        this.author=in.readParcelable(Authors.class.getClassLoader());
        this.column=in.readString();
        this.comments_count=in.readInt();
        this.type=in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.display_style);
        parcel.writeString(this.short_url);
        parcel.writeString(this.abs);
        parcel.writeInt(this.app_css);
        parcel.writeInt(this.like_count);
        parcel.writeTypedList(thumbs);
        parcel.writeString(this.create_time);
        parcel.writeInt(this.id);
        parcel.writeString(this.original_url);
        parcel.writeString(this.content);
        parcel.writeString(this.share_pic_url);
        parcel.writeString(this.type);
        parcel.writeTypedList(this.photos);
        parcel.writeString(this.public_time);
        parcel.writeString(this.url);
        parcel.writeParcelable(this.author,i);
        parcel.writeString(this.column);
        parcel.writeInt(this.comments_count);
        parcel.writeString(this.type);
    }
    public static class Thumbs implements Parcelable{

        public Medium medium;
        public String description;
        public Large large;
        public String tag_name;
        public Small small;
        public int id;
        @Override
        public int describeContents() {
            return 0;
        }
        public Thumbs(){}
        protected Thumbs(Parcel in){
            this.medium=in.readParcelable(Medium.class.getClassLoader());
            this.description=in.readString();
            this.large=in.readParcelable(Medium.class.getClassLoader());
            this.tag_name=in.readString();
            this.small=in.readParcelable(Medium.class.getClassLoader());
            this.id=in.readInt();
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.medium,i);
            parcel.writeString(this.description);
            parcel.writeParcelable(this.large,i);
            parcel.writeString(this.tag_name);
            parcel.writeParcelable(this.small,i);
            parcel.writeInt(this.id);
        }
        public static final Parcelable.Creator<Thumbs> CREATOR = new Parcelable
                .Creator<Thumbs>() {
            @Override
            public Thumbs createFromParcel(Parcel source) {
                return new Thumbs(source);
            }

            @Override
            public Thumbs[] newArray(int size) {
                return new Thumbs[size];
            }
        };
        public static class Medium implements Parcelable{
            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Medium(){}
            protected Medium(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();

            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Medium> CREATOR = new Parcelable
                    .Creator<Medium>() {
                @Override
                public Medium createFromParcel(Parcel source) {
                    return new Medium(source);
                }

                @Override
                public Medium[] newArray(int size) {
                    return new Medium[size];
                }
            };
        }
        public static class Large implements Parcelable{

            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Large(){}
            protected Large(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Large> CREATOR = new Parcelable
                    .Creator<Large>() {
                @Override
                public Large createFromParcel(Parcel source) {
                    return new Large(source);
                }

                @Override
                public Large[] newArray(int size) {
                    return new Large[size];
                }
            };
        }
        public static class Small implements Parcelable{

            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Small(){}
            protected Small(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Small> CREATOR = new Parcelable
                    .Creator<Small>() {
                @Override
                public Small createFromParcel(Parcel source) {
                    return new Small(source);
                }

                @Override
                public Small[] newArray(int size) {
                    return new Small[size];
                }
            };
        }
    }
    public static class Authors implements Parcelable {
        public boolean is_followed;
        public String editor_notes;
        public String uid;
        public String resume;
        public String url;
        public String avatar;
        public String name;
        public boolean is_special_user;
        public String last_post_time;
        public int n_posts;

        public boolean is_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
        }

        public boolean is_special_user() {
            return is_special_user;
        }

        public void setIs_special_user(boolean is_special_user) {
            this.is_special_user = is_special_user;
        }

        public boolean is_auth_author() {
            return is_auth_author;
        }

        public void setIs_auth_author(boolean is_auth_author) {
            this.is_auth_author = is_auth_author;
        }

        public String alt;
        public String large_avatar;
        public String id;
        public boolean is_auth_author;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.editor_notes);
            parcel.writeString(this.uid);
            parcel.writeString(this.resume);
            parcel.writeString(this.url);
            parcel.writeString(this.avatar);
            parcel.writeString(this.name);
            parcel.writeString(this.last_post_time);
            parcel.writeInt(this.n_posts);
            parcel.writeString(this.alt);
            parcel.writeString(this.large_avatar);
            parcel.writeString(this.id);
        }

        public Authors() {
        }

        protected Authors(Parcel in) {
            this.editor_notes=in.readString();
            this.uid=in.readString();
            this.resume=in.readString();
            this.url=in.readString();
            this.avatar=in.readString();
            this.name=in.readString();
            this.last_post_time=in.readString();
            this.n_posts=in.readInt();
            this.alt=in.readString();
            this.large_avatar=in.readString();
            this.id=in.readString();

        }

        public static final Parcelable.Creator<Authors> CREATOR = new Parcelable
                .Creator<Authors>() {
            @Override
            public Authors createFromParcel(Parcel source) {
                return new Authors(source);
            }

            @Override
            public Authors[] newArray(int size) {
                return new Authors[size];
            }
        };
    }
    public static class Photos implements Parcelable {
        public Medium medium;
        public String description;
        public Large large;
        public String tag_name;
        public Small small;
        public int id;
        @Override
        public int describeContents() {
            return 0;
        }
        public static class Medium implements Parcelable{
            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Medium(){}
            protected Medium(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();

            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Medium> CREATOR = new Parcelable
                    .Creator<Medium>() {
                @Override
                public Medium createFromParcel(Parcel source) {
                    return new Medium(source);
                }

                @Override
                public Medium[] newArray(int size) {
                    return new Medium[size];
                }
            };
        }
        public static class Large implements Parcelable{

            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Large(){}
            protected Large(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Large> CREATOR = new Parcelable
                    .Creator<Large>() {
                @Override
                public Large createFromParcel(Parcel source) {
                    return new Large(source);
                }

                @Override
                public Large[] newArray(int size) {
                    return new Large[size];
                }
            };
        }
        public static class Small implements Parcelable{

            public String url;
            public int width;
            public int height;
            @Override
            public int describeContents() {
                return 0;
            }
            public Small(){}
            protected Small(Parcel in){
                this.url=in.readString();
                this.width=in.readInt();
                this.height=in.readInt();
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.url);
                parcel.writeInt(this.width);
                parcel.writeInt(this.height);
            }
            public static final Parcelable.Creator<Small> CREATOR = new Parcelable
                    .Creator<Small>() {
                @Override
                public Small createFromParcel(Parcel source) {
                    return new Small(source);
                }

                @Override
                public Small[] newArray(int size) {
                    return new Small[size];
                }
            };
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.medium,i);
            parcel.writeString(this.description);
            parcel.writeParcelable(this.large,i);
            parcel.writeString(this.tag_name);
            parcel.writeParcelable(this.small,i);
            parcel.writeInt(this.id);
        }

        public Photos() {
        }

        protected Photos(Parcel in) {
            this.medium=in.readParcelable(Medium.class.getClassLoader());
            this.description=in.readString();
            this.large=in.readParcelable(Medium.class.getClassLoader());
            this.tag_name=in.readString();
            this.small=in.readParcelable(Medium.class.getClassLoader());
            this.id=in.readInt();

        }

        public static final Parcelable.Creator<Photos> CREATOR = new Parcelable
                .Creator<Photos>() {
            @Override
            public Photos createFromParcel(Parcel source) {
                return new Photos(source);
            }

            @Override
            public Photos[] newArray(int size) {
                return new Photos[size];
            }
        };
    }
    public DoubanMomentDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public static final Parcelable.Creator<DoubanMomentDetailBean> CREATOR = new Parcelable
            .Creator<DoubanMomentDetailBean>() {
        @Override
        public DoubanMomentDetailBean createFromParcel(Parcel source) {
            return new DoubanMomentDetailBean(source);
        }

        @Override
        public DoubanMomentDetailBean[] newArray(int size) {
            return new DoubanMomentDetailBean[size];
        }
    };
}

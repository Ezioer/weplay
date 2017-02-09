package com.example.weile.materialdesignexa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentListBean implements Parcelable {
    public int count;
    public int offset;
    public String date;
    public int total;
    public ArrayList<Posts> posts;

    public DoubanMomentListBean() {
    }

    public static class Posts implements Parcelable {

        public int display_style;
        public boolean is_editor_choice;
        public String published_time;
        public String url;
        public String short_url;
        public boolean is_liked;
        public Author author;
        public String column;

        public boolean is_editor_choice() {
            return is_editor_choice;
        }

        public void setIs_editor_choice(boolean is_editor_choice) {
            this.is_editor_choice = is_editor_choice;
        }

        public boolean is_liked() {
            return is_liked;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
        }

        public int app_css;
        @SerializedName("abstract")
        public String abs;
        public String date;
        public int like_count;
        public int comments_count;
        public ArrayList<Thumbs> thumbs;
        public String created_time;
        public String title;
        public String share_pic_url;
        public String type;
        public int id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.display_style);
            parcel.writeString(this.published_time);
            parcel.writeString(this.url);
            parcel.writeString(this.short_url);
            parcel.writeParcelable(this.author, i);
            parcel.writeString(this.column);
            parcel.writeInt(this.app_css);
            parcel.writeString(this.abs);
            parcel.writeString(this.date);
            parcel.writeInt(this.like_count);
            parcel.writeInt(this.comments_count);
            parcel.writeTypedList(thumbs);
            parcel.writeString(this.created_time);
            parcel.writeString(this.title);
            parcel.writeString(this.share_pic_url);
            parcel.writeString(this.type);
            parcel.writeInt(this.id);
        }

        public Posts() {
        }

        protected Posts(Parcel in) {
            this.display_style = in.readInt();
            this.published_time = in.readString();
            this.url = in.readString();
            this.short_url = in.readString();
            this.author = in.readParcelable(Author.class.getClassLoader());
            this.column = in.readString();
            this.app_css = in.readInt();
            this.abs = in.readString();
            this.date = in.readString();
            this.like_count = in.readInt();
            this.comments_count = in.readInt();
            this.thumbs = in.createTypedArrayList(Thumbs.CREATOR);
            this.created_time = in.readString();
            this.title = in.readString();
            this.share_pic_url = in.readString();
            this.type = in.readString();
            this.id = in.readInt();
        }

        public static final Parcelable.Creator<Posts> CREATOR = new Parcelable.Creator<Posts>() {
            @Override
            public Posts createFromParcel(Parcel source) {
                return new Posts(source);
            }

            @Override
            public Posts[] newArray(int size) {
                return new Posts[size];
            }
        };

        public static class Author implements Parcelable {
            public boolean is_followed;
            public String uid;
            public String url;
            public String avatar;
            public String name;
            public boolean is_special_user;
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
                parcel.writeString(this.uid);
                parcel.writeString(this.url);
                parcel.writeString(this.avatar);
                parcel.writeString(this.name);
                parcel.writeInt(this.n_posts);
                parcel.writeString(this.alt);
                parcel.writeString(this.large_avatar);
                parcel.writeString(this.id);
            }

            public Author() {
            }

            protected Author(Parcel in) {
                this.uid=in.readString();
                this.url=in.readString();
                this.avatar=in.readString();
                this.name=in.readString();
                this.n_posts=in.readInt();
                this.alt=in.readString();
                this.large_avatar=in.readString();
                this.id=in.readString();

            }

            public static final Parcelable.Creator<Author> CREATOR = new Parcelable
                    .Creator<Author>() {
                @Override
                public Author createFromParcel(Parcel source) {
                    return new Author(source);
                }

                @Override
                public Author[] newArray(int size) {
                    return new Author[size];
                }
            };
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected DoubanMomentListBean(Parcel in) {
        this.count = in.readInt();
        this.offset = in.readInt();
        this.date = in.readString();
        this.total = in.readInt();
        this.posts = in.createTypedArrayList(Posts.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.count);
        parcel.writeInt(this.offset);
        parcel.writeString(this.date);
        parcel.writeInt(this.total);
        parcel.writeTypedList(posts);
    }

    public static final Parcelable.Creator<DoubanMomentListBean> CREATOR = new Parcelable
            .Creator<DoubanMomentListBean>() {
        @Override
        public DoubanMomentListBean createFromParcel(Parcel source) {
            return new DoubanMomentListBean(source);
        }

        @Override
        public DoubanMomentListBean[] newArray(int size) {
            return new DoubanMomentListBean[size];
        }
    };
}

package com.example.weile.materialdesignexa.handle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weile on 2016/11/14.
 */
public class DBPlayHandle extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PlaybackDB";
    public static final String TABLE_PLAYBACK = "songs";
    public static final String SONG_KEY_ID = "song_id";
    public static final String SONG_KEY_REAL_ID = "song_real_id";
    public static final String SONG_KEY_LAST_PLAYED = "song_last_played";
    private Context mContext;
    public DBPlayHandle(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
         this.mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYBACK_SONG_TABLE = "CREATE TABLE " + TABLE_PLAYBACK + " (" +
                SONG_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SONG_KEY_REAL_ID + " INTEGER," +
                SONG_KEY_LAST_PLAYED + " INTEGER)";
        db.execSQL(CREATE_PLAYBACK_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYBACK);
    }
}

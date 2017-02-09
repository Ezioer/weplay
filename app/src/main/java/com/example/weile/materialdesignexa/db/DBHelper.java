package com.example.weile.materialdesignexa.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.TokenWatcher;

import com.example.weile.materialdesignexa.bean.LoveSong;
import com.example.weile.materialdesignexa.bean.Song;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "player.db";
    private final static int VERSION = 1;
    private static DBHelper instance = null;
    private SQLiteDatabase db = null;
    private Context mContext = null;
    //我喜欢的表
    private String MYLOVESONG = "CREATE TABLE if not exists mylovesong(id INTEGER PRIMARY KEY " +
            "AUTOINCREMENT NOT NULL,songid Text NOT NULL,songname Text NOT NULL,singer Text NOT " +
			"NULL,artistid Text NOT NULL,album Text NOT NULL,albumid Text NOT NULL)";
    //弹窗表
    private String POPDIALOG = "CREATE TABLE if not exists popdialog(id INTEGER PRIMARY KEY " +
			"AUTOINCREMENT NOT NULL,pid Text NOT NULL,times Text NOT NULL,date Text NOT NULL,ispop" +
			" Text NOT NULL)";

    //单例模式
    public synchronized static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public synchronized void opendatabase() {
        if (db == null) {
            db = getWritableDatabase();
        }
    }

    public synchronized void closedatabase() {
        if (db != null)
            db.close();
        db = null;
    }


    DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
        opendatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(MYLOVESONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion == 3) {
            //db.execSQL("drop table if exists history");
            upgradeTables(db);
        }
        this.onCreate(db);
    }

    //数据库升级
    public void upgradeTables(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            //创建新表
            db.execSQL(MYLOVESONG);
            //把旧表内容插入新表
            db.execSQL("INSERT INTO downtable (movieid, size, curpage,moviename,downTag,filesize)" +
					" SELECT movieid, size, curpage,moviename,downTag,filesize FROM downinfo;");
            //db.execSQL("drop table downinfo");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
    }


    //数据库SQL操作函数
    public Cursor querySql(String sql, String sqlstr[]) {
        Cursor cursor = db.rawQuery(sql, sqlstr);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //将当前歌曲添加到我喜欢的
    public void insertMyLove(Song song) {
        ContentValues values = new ContentValues();
        values.put("songid", song.getSongId());
        values.put("songname", song.getName());
        values.put("singer", song.getArtist());
        values.put("artistid", song.getArtistid());
        values.put("album", song.getAlbumName());
        values.put("albumid", song.getAlbumId());
        db.insert("mylovesong", null, values);
    }

    //选择所有喜欢的歌曲
    public ArrayList<LoveSong> selectLove() {
        ArrayList<LoveSong> list = new ArrayList<>();
        Cursor LoveCursor = querySql("select *from mylovesong", new String[]{});
        LoveCursor.moveToFirst();
        if (LoveCursor != null && LoveCursor.getCount() > 0) {
            do {
                long songid = LoveCursor.getLong(LoveCursor.getColumnIndex("songid"));
                String songname = LoveCursor.getString(LoveCursor.getColumnIndex("songname"));
                String singer = LoveCursor.getString(LoveCursor.getColumnIndex("singer"));
                long artistid = LoveCursor.getLong(LoveCursor.getColumnIndex("artistid"));
                long albumid = LoveCursor.getLong(LoveCursor.getColumnIndex("albumid"));
                String album = LoveCursor.getString(LoveCursor.getColumnIndex("album"));
                list.add(new LoveSong(songid, songname, singer, albumid, album, artistid));
            } while (LoveCursor.moveToNext());
            LoveCursor.close();
        }
        return list;
    }
    //查看歌曲是否被喜欢
    public boolean checkSongIslove(String  songid){
        Cursor islovecursor=querySql("select *from mylovesong where songid=?",new String[]{songid});
        if(islovecursor!=null&&islovecursor.getCount()>0){
              islovecursor.close();
            return true;
        }
        return false;
    }
    //从我喜欢的中移除
    public void deletelovesong(String songid){
        db.delete("mylovesong","songid=?",new String[]{songid});
    }
}

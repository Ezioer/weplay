package com.example.weile.materialdesignexa.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.util.AsyncListUtil;
import android.util.DisplayMetrics;

import com.example.weile.materialdesignexa.bean.Album;
import com.example.weile.materialdesignexa.bean.Artist;
import com.example.weile.materialdesignexa.bean.Song;

import java.util.ArrayList;

/**
 * Created by weile on 2016/11/14.
 */
public class SongUtils {
    //获得专辑列表
    public static ArrayList<Album> getAlbumList(Context mContext) {
        ArrayList<Album> mAlbumList = new ArrayList<>();
        System.gc();
        final String orderby = MediaStore.Audio.Albums.ALBUM;
        Cursor musiccursor = mContext.getContentResolver().query(MediaStore.Audio.Albums
                .EXTERNAL_CONTENT_URI, null, null, null, orderby);
        if (musiccursor != null && musiccursor.moveToFirst()) {
            int title = musiccursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int id = musiccursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int artist = musiccursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int songnum = musiccursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            int art = musiccursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                mAlbumList.add(new Album(musiccursor.getLong(id), musiccursor.getString(title),
                        musiccursor.getString(artist), false, musiccursor.getString(art),
                        musiccursor.getInt(songnum)));
            } while (musiccursor.moveToNext());
        }
        musiccursor.close();
        return mAlbumList;
    }

    //获得歌手列表
    public static ArrayList<Artist> getArtistList(Context mContext) {
        ArrayList<Artist> mArtistList = new ArrayList<>();
        System.gc();
        final String order = MediaStore.Audio.Artists.ARTIST;
        Cursor artistcursor = mContext.getContentResolver().query(MediaStore.Audio.Artists
                .EXTERNAL_CONTENT_URI, null, null, null, order);
        if (artistcursor != null && artistcursor.moveToFirst()) {
            int title = artistcursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int id = artistcursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int numalbum = artistcursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            int numsong = artistcursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            do {
                mArtistList.add(new Artist(artistcursor.getLong(id), artistcursor.getString
                        (title), artistcursor.getInt(numalbum), artistcursor.getInt(numsong)));
            } while (artistcursor.moveToNext());
        }
        artistcursor.close();
        return mArtistList;
    }

    //通过歌手获取其歌曲列表
    public static ArrayList<Song> getSongListOfArtist(Context mcontext, String artist) {
        ArrayList<Song> mSongList = new ArrayList<>();
        System.gc();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND " + MediaStore.Audio.Media
                .ARTIST + "='" + artist.replace("'", "''") + "'";
        final String ordervy = MediaStore.Audio.Media.TITLE;
        Cursor songcursor = mcontext.getContentResolver().query(MediaStore.Audio.Media
                .EXTERNAL_CONTENT_URI, null, where, null, ordervy);
        if (songcursor != null && songcursor.moveToFirst()) {
            int titleColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int artistidcolum = songcursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int addedDateColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int sizecolum = songcursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int kpbscolum = songcursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
            int typecolum = songcursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            int hzcolum = songcursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            do {
                mSongList.add(new Song(songcursor.getLong(idColumn),
                        songcursor.getString(titleColumn),
                        songcursor.getString(artistColumn),
                        songcursor.getString(pathColumn), false,
                        songcursor.getLong(albumIdColumn),
                        songcursor.getString(albumColumn),
                        songcursor.getLong(artistidcolum),
                        songcursor.getLong(addedDateColumn),
                        songcursor.getLong(songDurationColumn),songcursor.getString(sizecolum)
                        , songcursor.getString(kpbscolum), songcursor.getString(typecolum),songcursor.getString(hzcolum)));
            }
            while (songcursor.moveToNext());
        }
        songcursor.close();
        return mSongList;
    }

    //根据专辑id获取专辑歌曲
    public static ArrayList<Song> getAlbumSongByAlbumid(Context mContext, long albumid) {
        ArrayList<Song> mAlbumSong = new ArrayList<>();
        System.gc();
        String where = MediaStore.Audio.Media.ALBUM_ID + "=?";
        String wherevalue[] = {String.valueOf(albumid)};
        String orderby = MediaStore.Audio.Media._ID;
        Cursor songcursor = mContext.getContentResolver().query(MediaStore.Audio.Media
                .EXTERNAL_CONTENT_URI, null, where, wherevalue, orderby);
        if (songcursor != null && songcursor.moveToFirst()) {
            int titleColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int artistidcolum = songcursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int addedDateColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = songcursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int sizecolum = songcursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int kpbscolum = songcursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
            int typecolum = songcursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            int hzcolum = songcursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            do {
                mAlbumSong.add(new Song(songcursor.getLong(idColumn),
                        songcursor.getString(titleColumn),
                        songcursor.getString(artistColumn),
                        songcursor.getString(pathColumn), false,
                        songcursor.getLong(albumIdColumn),
                        songcursor.getString(albumColumn),
                        songcursor.getLong(artistidcolum),
                        songcursor.getLong(addedDateColumn),
                        songcursor.getLong(songDurationColumn), songcursor.getString(sizecolum)
                        , songcursor.getString(kpbscolum), songcursor.getString(typecolum),songcursor.getString(hzcolum)));
            }
            while (songcursor.moveToNext());
        }
        songcursor.close();
        return mAlbumSong;
    }

    //通过歌手获取其专辑列表
    public static ArrayList<Album> getAlbumListOfArtist(Context mContext, long artistid) {
        ArrayList<Album> mAlbumList = new ArrayList<>();
        System.gc();
        Cursor albumlist = mContext.getContentResolver().query(MediaStore.Audio.Artists.Albums
                .getContentUri("external", artistid), null, null, null, MediaStore.Audio.Albums
                .DEFAULT_SORT_ORDER);
        if (albumlist != null && albumlist.moveToFirst()) {
            int title = albumlist.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int id = albumlist.getColumnIndex(MediaStore.Audio.Albums._ID);
            int artist = albumlist.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int songnum = albumlist.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            int art = albumlist.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                mAlbumList.add(new Album(albumlist.getLong(id), albumlist.getString(title),
                        albumlist.getString(artist), false, albumlist.getString(art),
                        albumlist.getInt(songnum)));
            } while (albumlist.moveToNext());
        }
        albumlist.close();
        return mAlbumList;
    }

    //获得歌曲列表
    public static ArrayList<Song> getSongList(Context mContext) {
        ArrayList<Song> mSongList = new ArrayList<>();
        System.gc();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
        final String orderBy = MediaStore.Audio.Media.TITLE;
        Cursor musicCursor = mContext.getContentResolver().query(MediaStore.Audio.Media
                        .EXTERNAL_CONTENT_URI,
                null, where, null, orderBy);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int artistidcolum = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int sizecolum = musicCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int kpbscolum = musicCursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
            int typecolum = musicCursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            int hzcolum = musicCursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            do {
                mSongList.add(new Song(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        musicCursor.getString(pathColumn), false,
                        musicCursor.getLong(albumIdColumn),
                        musicCursor.getString(albumColumn),
                        musicCursor.getLong(artistidcolum),
                        musicCursor.getLong(addedDateColumn),
                        musicCursor.getLong(songDurationColumn), musicCursor.getString(sizecolum)
                        , musicCursor.getString(kpbscolum), musicCursor.getString(typecolum),musicCursor.getString(hzcolum)));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return mSongList;
    }

    //通过专辑id获取专辑图片路径
    public static String getAlbumArt(Context mContext, Long albumdId) {
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums
                        .EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(albumdId)},
                null);
        String imagePath = "";
        if (cursor != null && cursor.moveToFirst()) {
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        cursor.close();
        return imagePath;
    }

    public static enum PlayState {
        PLAYING, PAUSE;
    }
}

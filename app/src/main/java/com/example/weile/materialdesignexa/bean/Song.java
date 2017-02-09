package com.example.weile.materialdesignexa.bean;

import java.text.DecimalFormat;

/**
 * Created by architjn on 28/11/15.
 */
public class Song {

    private long songId, albumId, dateAdded;
    private String name;
    private String artist;
    private String path;
    private String albumName;
    private long artistid;
    private long duration;
    private boolean fav;
    private String size,kbps,type,hz;
    public Song() {
        super();
    }

    public String getSize() {
        float s=((float) Integer.valueOf(size))/(float)(1024*1024);
        return twoDec(s);
    }

    public String getType() {
        return type;
    }

    public String getHz() {
        return hz;
    }

    public String getKbps() {
        return kbps;
    }

    public Song(long songId, String name, String artist,
                String path, boolean fav, long albumId,
                String albumName, long artistid, long dateAdded, long duration, String size, String kbps, String type, String hz) {
        this.songId = songId;
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.fav = fav;
        this.dateAdded = dateAdded;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistid=artistid;
        this.duration = duration;
        this.size=size;
        this.type=type;
        this.kbps=kbps;
        this.hz=hz;

    }

    public long getAlbumId() {
        return albumId;
    }

    public long getSongId() {
        return songId;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getArtistid(){
        return artistid;
    }
    public String getPath() {
        return path;
    }

    public boolean isFav() {
        return fav;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public long getDurationLong() {
        return duration;
    }
    public long duration(){
        return duration/1000;
    }
    public long getSeconds(){
        return duration / 1000;
    }
    public String getDuration() {
        try {
            Long time = duration;
            long seconds = time / 1000;
            long minutes = seconds / 60;
            seconds = seconds % 60;

            if (seconds < 10) {
                return String.valueOf(minutes) + ":0" + String.valueOf(seconds);
            } else {
                return String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
        } catch (NumberFormatException e) {
            return String.valueOf(0);
        }
    }

    public String getFormatedTime(long duration) {
        try {
            Long time = duration;
            long seconds = time / 1000;
            long minutes = seconds / 60;
            seconds = seconds % 60;

            if (seconds < 10) {
                return String.valueOf(minutes) + ":0" + String.valueOf(seconds);

            } else {
                return String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
        } catch (NumberFormatException e) {
            return String.valueOf(0);
        }
    }
    public String twoDec(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}

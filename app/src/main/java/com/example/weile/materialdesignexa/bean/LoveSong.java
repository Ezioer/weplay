package com.example.weile.materialdesignexa.bean;

/**
 * Created by architjn on 28/11/15.
 */
public class LoveSong {

    private long songId, albumId,artistid;
    private String name;
    private String artist;
    private String albumName;

    public LoveSong() {
        super();
    }

    public LoveSong(long songId, String name, String artist,
                     long albumId,
                    String albumName, long artistid) {
        this.songId = songId;
        this.name = name;
        this.artist = artist;
        this.albumId = albumId;
        this.artistid=artistid;
        this.albumName = albumName;
    }

    public long getAlbumId() {
        return albumId;
    }
    public long getArtistId() {
        return artistid;
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
}

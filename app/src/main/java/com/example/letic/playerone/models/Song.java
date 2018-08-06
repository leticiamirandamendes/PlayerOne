package com.example.letic.playerone.models;

import java.util.Observable;

/**
 * Created by letic on 14/05/2018.
 */

public class Song extends Observable{

    private long id;
    private String title;
    private String artist;
    private String album;

    public Song(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Song(long id, String title, String artist, String album) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}

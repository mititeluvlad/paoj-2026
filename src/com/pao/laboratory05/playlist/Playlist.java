package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist{
    private String name;
    private Song[] songs;

    public Playlist(String name){
        this.name = name;
        this.songs = new Song[0];
    }

    public void addSong(Song song){
        int n = songs.length; 
        Song[] s = new Song[n+1];
        for(int i = 0; i < n; ++i){
            s[i] = songs[i];
        }
        s[n] = song;
        songs = s;
    }

    public String getName(){return name;}
    public Song[] getSongs(){return songs;}

    public void printSortedByTitle(){
        int n = songs.length;
        Song[] copy = songs.clone();
        Arrays.sort(copy);
        for(int i = 0; i < n; ++i){
            System.out.println(copy[i].toString());
        }
    }
    public void printSortedByDuration(){
        int n = songs.length;
        Song[] copy = songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        for(int i = 0; i < n; ++i){
            System.out.println(copy[i].toString());
        }
    }
    public int getTotalDuration(){
        int n = songs.length;
        int s = 0;
        for(int i = 0; i < n; ++i){
            s += songs[i].durationSeconds();
        }
        return s;
    }
}
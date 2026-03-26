package com.pao.laboratory05.playlist;

import java.util.Comparator;

public class SongDurationComparator implements Comparator<Song> {
    // compare: sortare după durationSeconds crescător
    @Override
    public int compare(Song s1, Song s2){
        if(s1.durationSeconds() > s2.durationSeconds()){
            return 1;
        }
        else if(s1.durationSeconds() < s2.durationSeconds()){
            return -1;
        }
        else 
            return 0;
    }
}
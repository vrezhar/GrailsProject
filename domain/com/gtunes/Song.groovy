package com.gtunes

class Song implements Comparable<Song>
{
    static searchable = [only: ['genre', 'title']];
    String title;
    String artist;
    Integer duration;
    Album album;
    String genre;
    static belongsTo = Album;

    static constraints =
            {
                title blank: false;
                artist blank: false;
                //album blank: false;
                duration min:1;
                genre blank: false;
            }
    static mapping = {
        sort "title"
    }
    int compareTo(Song other)
    {
        if(duration < other.duration)
            return -1;
        if(duration > other.duration)
            return 1;
        return 0;
    }
}

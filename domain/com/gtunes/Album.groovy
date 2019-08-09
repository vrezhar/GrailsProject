package com.gtunes

class Album
{
    static searchable = [only: ['genre', 'title']];
    String title;
    String genre;
    String artist;
    //byte[] art;
    List<Song> songs;
    static hasMany = [songs: Song];
    static belongsTo = Artist;

    static mappings =
            {
                songs cascade: 'save-update'
                //more information in hibernate's documentation
            }
    static constraints =
            {

            }
}

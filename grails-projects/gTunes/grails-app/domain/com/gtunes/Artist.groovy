package com.gtunes

class Artist
{
    static searchable = [only: ['name']]
    String name;
    static hasMany = [albums:Album];
    static constraints =
            {

            }
}

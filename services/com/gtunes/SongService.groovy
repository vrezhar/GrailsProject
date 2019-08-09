package com.gtunes

import grails.gorm.services.Service

@Service(Song)
interface SongService {

    Song get(Serializable id)

    List<Song> list(Map args)

    Long count()

    void delete(Serializable id)

    Song save(Song song)

}
package com.gtunes

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugin.cache.Cacheable

//@Transactional

class AlbumArtService
{

    static transactional = false
    static final DEFAULT_ALBUM_ART_IMAGE =
            "/images/no-album-art.gif"
    String artworkRequestUrl = 'http://itunes.apple.com/search?media=music&entity=album&attribute=albumTerm'
    @Cacheable('albumArt')
    def getAlbumArt(String artist, String album) {
        def imageUrl = DEFAULT_ALBUM_ART_IMAGE
        if(artist && album) {
            try {
                def restBuilder = new RestBuilder()
                def urlWithAlbumParam =
                        "${artworkRequestUrl}&term=${album.encodeAsURL()}"
                def response = restBuilder.get(urlWithAlbumParam)
                def json = response.json
                def records = json.results
                def matchingRecord = records.find { r ->
                    r.artistName == artist && r.collectionName == album
                }
                imageUrl = matchingRecord?.artworkUrl100 ?: DEFAULT_ALBUM_ART_IMAGE
                return  imageUrl;
            } catch (Exception e) {
                log.error ("Problem retrieving artwork: ${e.message}", e,imageUrl)
            }
        }
    }
}


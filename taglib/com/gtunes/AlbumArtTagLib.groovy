package com.gtunes

class AlbumArtTagLib
{
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = 'music'
    AlbumArtService albumArtService
    def albumArt = { attrs, body ->
        def artist = attrs.remove('artist')?.toString()
        def album = attrs.remove('album')?.toString()
        def width = attrs.remove('width') ?: 200
        if(artist && album) {
            def albumArt = albumArtService.getAlbumArt(artist, album)
            if(albumArt.startsWith("/")) {
                albumArt = "${request.contextPath}${albumArt}"
            }
            out << "<img width=\"$width\" src=\"${albumArt}\" border=\"0\""
            attrs.each { k,v-> out << "$k=\"${v?.encodeAsHTML()}\" "}
            out << "></img>"
        }
    }
}

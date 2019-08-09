package com.gtunes


//mappings for just single controllers can also be defined,
//to do so, create a class Controller_NameUrlMappings under grails-app/conf/

class UrlMappings {

    static mappings = {
        "/"(controller:"store", action: "index");
        "/showAlbum/$id"(controller:'album', action:'show') {
                    format = 'simple' //request.format == simple
                }
        "/showAlbumDetailed/$id"(controller:'album', action:'show') {
                    format = 'detailed' //request.format == detailed
                }
        name showArtistDetails: "/showArtist/$artistName"{
                    controller = 'artist'
                    action = [GET: 'show',
                              POST: 'save',
                              PUT: 'update',
                              DELETE: 'delete']
                } //also possible (resource: 'artist'), replacing the closure
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        //urls can be generated in views via g:link tag
        //g:link supports optional mapping attribute, corresponding to the name of the mappings given here
        //alternative to this is specifying  the mapping's name in the link tag
        //
    }
}

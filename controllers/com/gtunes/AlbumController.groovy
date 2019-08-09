package com.gtunes

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ALbumCreateCommand
{
    String artist
    String title
    List<Song> songs = []
    List<Integer> durations = []

    static constraints =
            {
                artist blank: false
                title blank: false
                songs minSize: 1, validator: {
                    val,
                    obj ->
                    if (val.size() != obj.durations.size())
                        return "songs.durations.not.equal.size"
                }
            }
    Album createAlbum()
    {
        def artist = Artist.findByName(artist) ?: new Artist(name:artist)
        def album = new Album(title:title)
        songs.eachWithIndex
                {
                    songTitle,
                    i -> album.addToSongs(title:songTitle, duration:durations[i])
                }
        return album
    }

}

class AlbumController
{
    def beforeInterceptor =
            {
                log.trace("Executing action $actionName with params $params")
            }

    AlbumService albumService;
    //static scaffold = Album;
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond albumService.list(params), model:[albumCount: albumService.count()]
    }

    def show() {
        respond albumService.get(params.id)
        //using Album.read(params.id) is also possible, the retrieved object isn't persisted
    }

    def create() {
        respond new Album(params)
    }
/*
    def save(AlbumCreateCommand cmd)
    {
        if(cmd.validate())
        {
            def album = cmd.createAlbum()
            album.save()


            //to explicitly tell hibernate to issue an SQL insert query, use album.save(insert:true)
            //to make sure that hibernate persists the data to the db immediately, use save(flush: true)
            //when flush() is called anything that wasn't previously persisted but was cached is persisted
            //

            redirect(action:"show", id:album.id)
        }
        else
        {
            render(view:"create", model:[cmd:cmd])
        }
    }
*/
    def save(Album album) {
        if (album == null) {
            notFound()
            return
        }

        try {
            albumService.save(album)
        } catch (ValidationException e) {
            respond album.errors, view:'create'
            return
        }



        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'album.label', default: 'Album'), album.id])
                redirect album
            }
            '*' { respond album, [status: CREATED] }
        }
    }

/*
    def save()
    {
       def album = Album.get(params.id)
       bindData(album, params, [include:"title"])
       album.properties = params
       if(album.save())
       {
            redirect action: "show", id: album.id
       }
        else
        {
            render view: "edit", model: [album:album]
        }
    }
*/


    def edit(Long id) {
        respond albumService.get(id)
    }

    def update(Album album) {
        if (album == null) {
            notFound()
            return
        }

        try {
            albumService.save(album)
        } catch (ValidationException e) {
            respond album.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'album.label', default: 'Album'), album.id])
                redirect album
            }
            '*'{ respond album, [status: OK] }
        }
    }

    /*
    def update()
    {
        def album = Album.get(params.id)
        album.properties = params
        album.save()
    }
    */

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        albumService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'album.label', default: 'Album'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

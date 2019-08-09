package com.gtunes

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ArtistController {

    //ArtistService artistService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        //respond artistService.list(params), model:[artistCount: artistService.count()]
    }

    def show(Long id)
    {
        def nameToSearchFor = params.artistName.replaceAll('_', ' ');
        def artist = Artist.findByName(nameToSearchFor);
        //
        //respond artistService.get(id);
    }

    def create()
    {
        respond new Artist(params)
    }

    def save(Artist artist) {
        if (artist == null) {
            notFound()
            return
        }
/*
        try {
            artistService.save(artist)
        } catch (ValidationException e) {
            respond artist.errors, view:'create'
            return
        }
*/
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'artist.label', default: 'Artist'), artist.id])
                redirect artist
            }
            '*' { respond artist, [status: CREATED] }
        }
    }

    def edit(Long id) {
        //respond artistService.get(id)
    }

    def update(Artist artist) {
        if (artist == null) {
            notFound()
            return
        }

        try {
            artistService.save(artist)
        } catch (ValidationException e) {
            respond artist.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'artist.label', default: 'Artist'), artist.id])
                redirect artist
            }
            '*'{ respond artist, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        //artistService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'artist.label', default: 'Artist'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

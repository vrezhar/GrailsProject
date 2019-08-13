package com.gtunes

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
class SongController {

    SongService songService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond songService.list(params), model:[songCount: songService.count()]
    }

    def show(Long id) {
        render view: "show", model: [ song: songService.get(id) ]
    }

    def create() {
        respond new Song(params)
    }

    def save(Song song) {
        if (song == null) {
            notFound()
            return
        }

        try {
            songService.save(song)
        } catch (ValidationException e) {
            respond song.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'song.label', default: 'Song'), song.id])
                redirect song
            }
            '*' { respond song, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond songService.get(id)
    }

    def update(Song song) {
        if (song == null) {
            notFound()
            return
        }

        try {
            songService.save(song)
        } catch (ValidationException e) {
            respond song.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'song.label', default: 'Song'), song.id])
                redirect song
            }
            '*'{ respond song, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        songService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'song.label', default: 'Song'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'song.label', default: 'Song'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

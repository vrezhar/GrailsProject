package com.gtunes;
import grails.gorm.DetachedCriteria
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_ADMIN','ROLE_USER'])
class StoreController
{

    def showTime()
    {
        render "The time is ${new Date()}"
    }

    def shop()
    {
        def genreList = new DetachedCriteria(Album).distinct('genre').list()
        [genres:genreList.sort()]
    }

    def genre()
    {
        def max = Math.min(params.int('max') ?: 10, 100)
        def offset = params.int('offset') ?: 0
        def total = Album.countByGenre(params.name)
        def albumList = Album.withCriteria
                {
                    eq 'genre', params.name
                    projections {
                                artist { order 'name' }
                            }
                    maxResults max
                    firstResult offset
                }
        return [albums:albumList,
                totalAlbums:total,
                genre:params.name]
    }

    def index()
    {
        //unnecessary
        render view: "/index"
    }
}

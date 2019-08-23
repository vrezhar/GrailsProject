package com.secured.auth


import org.springframework.web.bind.annotation.RequestMapping
import grails.plugin.springsecurity.annotation.Secured

//@Secured(['ROLE_ADMIN','ROLE_USER'])
class MainController {
    static defaultAction = 'home'

    @Secured(['ROLE_ADMIN'])
    def list()
    {
        render(view:"/user/userList",model: [users: User.list()])
    }


    @Secured(['ROLE_ADMIN','ROLE_USER'])
    def home()
    {
        render view:"home", model: [user:  'Test']
    }

    @Secured(['ROLE_USER'])
    def confirm()
    {

    }

}

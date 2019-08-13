package com.gtunes

import grails.plugin.springsecurity.annotation.Secured

class LoginCommand
{
    String login
    String password
    private usr
    SecureUser getUser()
    {
        if(!usr && login)
        {
            usr = SecureUser.findByUsername(login, [fetch:[purchasedSongs:'join']])
        }
        return usr
    }

    static constraints =
            {
                login blank:false, validator:{
                    val,
                    obj ->
                        if(!obj.user)
                            return "user.not.found"
                }
                password blank:false, validator:{
                    val,
                    obj ->
                        if(obj.user && obj.user.password != val)
                            return "user.password.invalid"
                }
            }
}
@Secured(['ROLE_USER','ROLE_ADMIN'])
class UserController
{

    def showTime()
    {
        new Date()
    }
    def welcome()
    {
        render view: '/user/welcome'
    }
    def register()
    {
        if(request.method == 'POST')
        {
            def usr = new SecureUser()
            def userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
            usr.properties['username', 'password', 'firstName', 'lastName' ] = params




            if(usr.password != params.confirm)
            {
                usr.errors.rejectValue("password", "user.password.dontmatch")
                return [user: usr]

            }
            else if(SecureUserRole.create(usr,userRole))
            {
                //using the session scope, user needs to be active untill loging out
                session.user = usr
                //if(usr.getAuthorities()[0].authority == 'ROLE_USER')
                    redirect controller:'store'
            }
            else
            {
                return [user:usr]
            }
        }
    }
    def login(LoginCommand cmd)
    {
        if(request.method == 'POST')
        {
            if(!cmd.hasErrors())
            {
                session.user = cmd.getUser()
                //if(cmd.getUser().authorities.contains(adminRole))
                //    render 'admin logged in!'
                 //else
                redirect controller: 'store', action: 'index'
            }
            else
            {
                render view:'/store/index', model:[loginCmd:cmd]
            }

        }
        else
        {
            render view:'/store/index'
        }
    }
}

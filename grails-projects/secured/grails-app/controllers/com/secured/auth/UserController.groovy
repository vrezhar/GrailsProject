package com.secured.auth

import grails.plugin.springsecurity.annotation.Secured

class UserController {

    def register()
    {
        if(request.method == 'POST') {
            def userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
            User u = new User()
            u.username = params.username
            u.password = params.password
            u.firstName = params.firstName
            u.lastName = params.lastName
            u.authorities.add(userRole)
            u.accountExpired = false
            u.accountLocked = false
            u.passwordExpired = false
            //u.properties['username', 'password', 'firstName', 'lastName'] = params
            if(u.password != params.confirm) {
                u.errors.rejectValue("password", "user.password.dontmatch")
                return [user: u]
            } else if(UserRole.create(u,userRole)) {
                session.user = u
                redirect controller: 'main',action:'confirm'
            } else {
                render body: "something went horribly wrong",model: [user: u]
            }
        }
    }
}

package com.secured.auth


import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class UserController  {

    def patternValidator
    def securityCoordinateGenerator

    def register()
    {
        if(request.method == 'POST')
        {

            def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
            User u = User.findOrSaveWhere(username: params.username,firstName: params.firstName, lastName: params.lastName)
            u.password = params.password
            if(!patternValidator.validateUsername(u.username))
            {
                u.errors.rejectValue("username","user.username.incorrect")
                return [user: u]
            }
            def securityCard = securityCoordinateGenerator.generateCoordinates()
            if (u.password != params.confirm)
            {
                u.errors.rejectValue("password", "user.password.dontmatch")
                return [user: u]
            }
            else
            {
                securityCard.each {k,v ->
                    u.addToCoordinates(new SecurityCoordinate(position: k,value: v,user: u))
                }
                u.save()
                UserRole.create(u,userRole,true)
                flash.securitycard = securityCard
                redirect controller: 'main',action:'confirm'
            }

        }
    }

}

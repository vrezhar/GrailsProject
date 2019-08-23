package com.secured.auth

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.plugin.springsecurity.annotation.Secured

class UserController implements GrailsConfigurationAware {

    List<String> coordinatePositions;
    Random r = new Random()
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
            Map<String, String> securityCard = [:]
            Collections.shuffle(coordinatePositions)
            for(entry in coordinatePositions)
            {
                securityCard.put(entry,r.nextInt(100).toString())
            }
            securityCard.each { k, v ->
                u.addToCoordinates(new SecurityCoordinate(position: k, value: v, user: u))
            }
            //u.properties['username', 'password', 'firstName', 'lastName'] = params
            if(u.password != params.confirm) {
                u.errors.rejectValue("password", "user.password.dontmatch")
                return [user: u]
            } else if(u.save()) {
                session.user = u
                UserRole.create(u,userRole)
                redirect controller: 'main',action:'confirm'
            } else {
                render body: "something went horribly wrong",model: [user: u]
            }
        }
    }

    @Override
    void setConfiguration(Config co) {
        coordinatePositions = co.getProperty('security.coordinate.positions', List, []) as List<String>
    }
}

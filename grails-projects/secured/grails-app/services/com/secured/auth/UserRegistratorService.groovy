package com.secured.auth


import grails.gorm.transactions.Transactional

@Transactional
class UserRegistratorService implements IUserService {

    def securityCoordinateGenerator
    def patternValidator

    @Override
    def generateCoordinates() {
        return securityCoordinateGenerator.generateCoordinates()
    }


    @Override
    def exists(String username) {
        def usr = User.findWhere(username: username)
        return usr
    }

    @Override
    def hasRole(User usr,Role r)
    {
        return usr.authorities.contains(r)
    }

    @Override
    def hasRole(User usr,String role)
    {
        def authority = Role.findOrSaveWhere(authority: role)
        if(authority)
        {
            return usr.authorities.contains(authority)
        }
        return false
    }

    @Override
    Map create(String username,String password,String firstName, String lastName)
    {
        if(username == "" || password == "")
            return [user: null, card: null, error: "Wrong username or password"]
        if(!patternValidator.validateUsername(username))
            return [user: null, card: null, error: "Username must be at least 3 characters long and contain at least one lowercase letter"]
        if(!(passwordComplexity(password)>1))
             return [user: null, card: null, error: "Wrong password, use numbers, lowercase and uppercase characters and make sure it is 5-16 characters long"]
        def already_exists = exists(username)
        if(already_exists)
        {
            Map card = [:];
            def coordinates = SecurityCoordinate.findAllByUser(already_exists)
            for(coordinate in coordinates)
            {
                card.put(coordinate.position,coordinate.value)
            }

            return [user: already_exists,card: card, error: "User already exists"]
        }
        def usr = new User(username: username,password: password,firstName: firstName,lastName: lastName)
        def securityCard = generateCoordinates()
        usr.addToCoordinates(securityCard)
        usr.accountExpired = false
        usr.accountLocked = false
        usr.passwordExpired = false
        if(usr.validate())
        {
            usr.save()
            assignRole(usr,"ROLE_USER")
            return [user: usr,card: securityCard, error: "Success"]
        }
        else  return [user: usr, card: securityCard, error: "Validation error"]
    }

    @Override
    int passwordComplexity(String password) {
        return patternValidator.validatePassword(password)
    }

    @Override
    def assignRole(User usr, String role) {
        Role new_role = Role.findOrSaveWhere(authority: role)
        if (!usr.authorities.contains(new_role)) {
            UserRole.create(usr, new_role, true)
        }
    }

    @Override
    def assignRole(User usr,Role r)
    {
        if(!Role.findWhere(authority:  r.authority))
            r.save()
        if (!usr.authorities.contains(r)) {
            UserRole.create(usr, r, true)
        }
    }

    @Override
    def addToCoordinates(User usr, Map coordinates) {
        coordinates.each{k,v->
            usr.addToCoordinates(new SecurityCoordinate(position: k, value: v, user: usr))
        }
    }
}
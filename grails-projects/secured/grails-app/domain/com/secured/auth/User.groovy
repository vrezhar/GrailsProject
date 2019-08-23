package com.secured.auth

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1
    //transient springSecurityService

    String username
    String password
    String firstName
    String lastName

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    static hasMany = [coordinates: SecurityCoordinate]

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }
/*
    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }


    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

*/
    static transients = ['springSecurityService']
    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        firstName nullable: false
        lastName nullable: false
    }

    static mapping = {
	    password column: '`password`'
        //autowire true
    }


}

class LoginCommand
{
    String username
    String password
    private User u
    User getUser() {
        if(!u && username) {
            u = User.findByUsername(username)
        }
        return u
    }
    static constraints = {
        username blank:false, validator:{ val, obj ->
            if(!obj.user)
                return "user.not.found"
        }
        password blank:false, validator:{ val, obj ->
            if(obj.user && obj.user.password != val)
                return "user.password.invalid"
        }
    }
}

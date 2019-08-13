package com.gtunes

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class SecureUser implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String firstName;
    String lastName;
    //static hasMany = [purchasedSongs:Song];

    //Set<Role> authorities;
    //static transients = ['authorities']


    Set<Role> getAuthorities() {
        (SecureUserRole.findAllBySecureUser(this) as List<SecureUserRole>)*.role as Set<Role>
    }

    static constraints =
            {
                username blank:false, size:5..15,matches:/[\S]+/, unique:true
                password blank:false, size:5..100
                firstName blank:false
                lastName blank:false
            }

    static mapping = {
	    password column: '`password`'

        }

}

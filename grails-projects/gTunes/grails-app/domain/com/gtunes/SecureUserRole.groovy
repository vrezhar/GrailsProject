package com.gtunes

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class SecureUserRole implements Serializable {

	private static final long serialVersionUID = 1

	SecureUser secureUser
	Role role

	@Override
	boolean equals(other) {
		if (other instanceof SecureUserRole) {
			other.secureUserId == secureUser?.id && other.roleId == role?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (secureUser) {
            hashCode = HashCodeHelper.updateHash(hashCode, secureUser.id)
		}
		if (role) {
		    hashCode = HashCodeHelper.updateHash(hashCode, role.id)
		}
		hashCode
	}

	static SecureUserRole get(long secureUserId, long roleId) {
		criteriaFor(secureUserId, roleId).get()
	}

	static boolean exists(long secureUserId, long roleId) {
		criteriaFor(secureUserId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long secureUserId, long roleId) {
		SecureUserRole.where {
			secureUser == SecureUser.load(secureUserId) &&
			role == Role.load(roleId)
		}
	}

	static SecureUserRole create(SecureUser secureUser, Role role, boolean flush = false) {
		def instance = new SecureUserRole(secureUser: secureUser, role: role)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(SecureUser u, Role r) {
		if (u != null && r != null) {
			SecureUserRole.where { secureUser == u && role == r }.deleteAll()
		}
	}

	static int removeAll(SecureUser u) {
		u == null ? 0 : SecureUserRole.where { secureUser == u }.deleteAll() as int
	}

	static int removeAll(Role r) {
		r == null ? 0 : SecureUserRole.where { role == r }.deleteAll() as int
	}

	static constraints = {
	    secureUser nullable: false
		role nullable: false, validator: { Role r, SecureUserRole ur ->
			if (ur.secureUser?.id) {
				if (SecureUserRole.exists(ur.secureUser.id, r.id)) {
				    return ['userRole.exists']
				}
			}
		}
	}

	static mapping = {
		id composite: ['secureUser', 'role']
		version: false
	}
}

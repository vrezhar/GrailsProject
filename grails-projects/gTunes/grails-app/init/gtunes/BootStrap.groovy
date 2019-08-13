package gtunes

import com.gtunes.Role
import com.gtunes.SecureUser
import com.gtunes.SecureUserRole

class BootStrap {

    def init = { servletContext ->

        def adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        //def userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')


        def admin = SecureUser.findOrSaveWhere(username: 'admin',password: '1Test',firstName: 'Admin', lastName: 'Admin')
        //def mockUser = SecureUser.findOrSaveWhere(username: 'user', password: 'Muser1', firstName: 'Mock', lastName: 'Mock')

        if(!admin.authorities.contains(adminRole))
            SecureUserRole.create(admin,adminRole)


        //if(!mockUser.authorities.contains(userRole))
        //    SecureUserRole.create(mockUser,userRole)
    }
    def destroy = {
    }
}

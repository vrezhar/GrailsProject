package secured


class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/register"(controller: 'user',action: 'register')
        "/"(view: '/index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

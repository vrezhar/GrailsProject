package com.demo

class UrlMappings {

    static mappings = {
        "/car"(controller:"car")
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

       // "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

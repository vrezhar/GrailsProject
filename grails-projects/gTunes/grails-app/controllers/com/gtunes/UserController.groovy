package com.gtunes

class LoginCommand
{
    String login
    String password
    private usr
    User getUser()
    {
        if(!usr && login)
        {
            usr = User.findByLogin(login, [fetch:[purchasedSongs:'join']])
        }
        return usr
    }

    static constraints =
            {
                login blank:false, validator:{
                    val,
                    obj ->
                        if(!obj.user)
                            return "user.not.found"
                }
                password blank:false, validator:{
                    val,
                    obj ->
                        if(obj.user && obj.user.password != val)
                            return "user.password.invalid"
                }
            }
}

class UserController
{

    def register()
    {
        if(request.method == 'POST')
        {
            def usr = new User()
            usr.properties['login', 'password', 'firstName', 'lastName'] = params
            if(usr.password != params.confirm)
            {
                usr.errors.rejectValue("password", "user.password.dontmatch")
                return [user: usr]

            }
            else if(usr.save())
            {
                //using the session scope, user needs to be active untill loging out
                session.user = usr
                redirect controller:"store"
            }
            else
            {
                return [user:usr]
            }
        }
    }
    def login(LoginCommand cmd)
    {
        if(request.method == 'POST')
        {
            if(!cmd.hasErrors())
            {
                session.user = cmd.getUser()
                redirect controller:'store'
            }
            else
            {
                render view:'/store/index', model:[loginCmd:cmd]
            }

        }
        else
        {
            render view:'/store/index'
        }
    }
}

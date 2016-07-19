import it.test.OAuthService
import it.test.OAuthToken
import it.test.User
import it.test.UserService
import ratpack.exec.Execution

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        OAuthService oAuthService = new OAuthService()
        UserService userService = new UserService(oAuthService)
        bindInstance(OAuthService, oAuthService)
        bindInstance(UserService, userService)
    }
    handlers {
        all { ctx ->
            System.out.println("Adding token to ctx.next registry")
            if (ctx.request.queryParams?.work == 'true') {
                Execution.current().add(OAuthToken, new OAuthToken(token: 'abcd')) // works
                next()
            } else {
                next(single(OAuthToken, new OAuthToken(token: 'abcd')))
            }
        }
        all { ctx ->
            OAuthToken token = ctx.get(OAuthToken)
            System.out.println("Token is avaliable in next handler ${token.token}")
            next()
        }
        get(":name") { ctx ->

            OAuthToken token = ctx.get(OAuthToken)
            System.out.println("Token is avaliable in final handler ${token.token}")

            UserService userService = ctx.get(UserService)
            userService.getUser()
            .then({ User user ->
                System.out.println("Token is avaliable in service ${token.token}")
                ctx.render("All Done")
            })

        }
    }
}

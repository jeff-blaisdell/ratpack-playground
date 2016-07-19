package it.test

import ratpack.exec.Promise;

class UserService {

    OAuthService oAuthService

    UserService(OAuthService oAuthService) {
        this.oAuthService = oAuthService
    }

    Promise<User> getUser() {
        return oAuthService.getToken()
        .map({ OAuthToken token ->
            return new User(name: "fred", token: token)
        })
    }
}

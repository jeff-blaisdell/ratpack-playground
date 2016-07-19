package it.test

import ratpack.exec.Execution
import ratpack.exec.Promise
import ratpack.service.Service

class OAuthService implements Service {

    Promise<OAuthToken> getToken() {
        return Promise.value(Execution.current().get(OAuthToken))
    }

}

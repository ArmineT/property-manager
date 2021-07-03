package com.property.manager.rest.uri;

public interface AuthUri {
    String baseUri = BaseRestUri.baseUri + "authentication/";

    //registration
    String REGISTER = baseUri + "register";

    //login, logout
    String LOGIN = baseUri + "login";

    //refresh token
    String REFRESH_TOKEN = baseUri + "refreshToken";
}

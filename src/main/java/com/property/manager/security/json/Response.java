package com.property.manager.security.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

    private static final String OK_MSG = "OK";
    private static final String CODE_OK = "OK";

    //    private final String success;
    private final String message;

    private T response;

    public Response(String success, String message, T response) {
//        this.success = success;
        this.message = message == null ? Response.OK_MSG : message;
        this.response = response;
    }

    public Response(String message, T response) {
//        this.success = success;
        this.message = message == null ? Response.OK_MSG : message;
        this.response = response;
    }
}

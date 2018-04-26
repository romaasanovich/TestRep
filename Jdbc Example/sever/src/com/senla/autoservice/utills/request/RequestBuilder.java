package com.senla.autoservice.utills.request;

import java.util.ArrayList;
import java.util.List;

public class RequestBuilder {

    private final List<Object> arguments = new ArrayList<>();
    private String method;

    public RequestBuilder setMethod(final String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setArgument(final Object argument) {
        arguments.add(argument);
        return this;
    }

    public Request create() {
        return new Request(method, arguments);
    }
}
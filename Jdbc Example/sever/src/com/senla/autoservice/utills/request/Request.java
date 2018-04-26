package com.senla.autoservice.utills.request;

import java.util.List;

public class Request{

    private final List<Object> arguments;
    private String method;

    public Request(final String method, final List<Object> arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public List<Object> getArguments() {
        return arguments;
    }

}
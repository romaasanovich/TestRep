package com.senla.autoservice.utills.response;

public class Response {

    private Status status;

    private String response;

    public Response() {
    }

    public Response(final Status status, final String response) {
        this.status = status;
        this.response = response;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public String getOutput() {
        return response;
    }

    public void setResponse(final String status) {
        response = status;
    }
}

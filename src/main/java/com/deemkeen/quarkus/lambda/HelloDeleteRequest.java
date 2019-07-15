package com.deemkeen.quarkus.lambda;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class HelloDeleteRequest {

    private String id;

    public HelloDeleteRequest(){

    }

    public HelloDeleteRequest(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

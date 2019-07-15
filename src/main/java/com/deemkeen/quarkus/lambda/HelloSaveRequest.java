package com.deemkeen.quarkus.lambda;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class HelloSaveRequest {

    private String firstName;
    private String lastName;

    public HelloSaveRequest(){

    }

    public HelloSaveRequest(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

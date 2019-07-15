package com.deemkeen.quarkus.util;

public class LambdaUtil {

    private LambdaUtil(){

    }

    public static String greet(String first, String last) {
        return String.format("Hello, %s %s!", first, last);
    }

}

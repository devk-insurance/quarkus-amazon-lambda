package com.deemkeen.quarkus.lambda;

import com.deemkeen.quarkus.util.HttpMethod;

public interface LambdaProcessable {
    String process(String input) throws Exception;
    String getResourceId();
    HttpMethod getHttpMethod();
}

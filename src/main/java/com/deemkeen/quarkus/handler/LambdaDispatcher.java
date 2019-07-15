package com.deemkeen.quarkus.handler;

import com.deemkeen.quarkus.lambda.LambdaProcessable;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LambdaDispatcher {

    @Inject
    @Any
    Instance<LambdaProcessable> functions;

    String dispatch(String resourceId, String httpMethod, String message) throws Exception {
        for (LambdaProcessable f : functions) {
            if (resourceId.equals(f.getResourceId()) && httpMethod.equals(f.getHttpMethod().name())) {
                return f.process(message);
            }
        }

        throw new Exception("LambdaDispatcher dispatch");
    }

}

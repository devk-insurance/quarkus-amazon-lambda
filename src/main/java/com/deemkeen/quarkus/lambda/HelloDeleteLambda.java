package com.deemkeen.quarkus.lambda;

import com.deemkeen.quarkus.handler.DynamoDao;
import com.deemkeen.quarkus.util.Constants;
import com.deemkeen.quarkus.util.HttpMethod;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;

@Singleton
public class HelloDeleteLambda extends AbstractDynamoLambda implements LambdaProcessable {

    @Inject
    public HelloDeleteLambda(Jsonb jsonb, DynamoDao dynamoDao) {
        super(jsonb, dynamoDao);
    }

    @Override
    public String process(String message) throws Exception{

        try {
            HelloDeleteRequest request;
            request = jsonb.fromJson(message, HelloDeleteRequest.class);
            HelloDynamoResponse response = dynamoDao.delete(request.getId());

            return jsonb.toJson(response);
        } catch (Exception e) {
            throw new Exception("HelloDeleteLambda process");
        }


    }

    @Override
    public String getResourceId() {
        return Constants.HELLO_PATH;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }
}

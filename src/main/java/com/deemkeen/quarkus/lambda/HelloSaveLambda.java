package com.deemkeen.quarkus.lambda;

import com.deemkeen.quarkus.handler.DynamoDao;
import com.deemkeen.quarkus.util.Constants;
import com.deemkeen.quarkus.util.HttpMethod;
import com.deemkeen.quarkus.util.LambdaUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;

@Singleton
public class HelloSaveLambda extends AbstractDynamoLambda implements LambdaProcessable {

    @Inject
    public HelloSaveLambda(Jsonb jsonb, DynamoDao dynamoDao) {
        super(jsonb, dynamoDao);
    }

    @Override
    public String process(String message) throws Exception{

        try {
            HelloSaveRequest request = jsonb.fromJson(message, HelloSaveRequest.class);
            String greeting = LambdaUtil.greet(request.getFirstName(), request.getLastName());
            HelloDynamoResponse response = dynamoDao.save(greeting);

            return jsonb.toJson(response);
        } catch (Exception e) {
            throw new Exception("HelloSaveLambda process");
        }

    }

    @Override
    public String getResourceId() {
        return Constants.HELLO_PATH;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}

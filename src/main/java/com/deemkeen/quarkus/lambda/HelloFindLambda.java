package com.deemkeen.quarkus.lambda;

import com.deemkeen.quarkus.handler.DynamoDao;
import com.deemkeen.quarkus.util.Constants;
import com.deemkeen.quarkus.util.HttpMethod;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class HelloFindLambda extends AbstractDynamoLambda implements LambdaProcessable {

    private static final int FIND_LIMIT = 10;

    @Inject
    public HelloFindLambda(Jsonb jsonb, DynamoDao dynamoDao) {
        super(jsonb, dynamoDao);
    }

    @Override
    public String process(String message) throws Exception {

        // try it with the generic way
        //Map hashMap = jsonb.fromJson(message, new HashMap<String, Integer>(){}.getClass().getGenericSuperclass());

        Map params = jsonb.fromJson(message, HashMap.class);

        if(!params.isEmpty()){
            String id = (String) params.get("id");
            if(id != null){
                HelloDynamoResponse helloDynamoResponse = dynamoDao.find(id);
                return jsonb.toJson(helloDynamoResponse);
            }
        } else {
            List<HelloDynamoResponse> byLimit = dynamoDao.findByLimit(FIND_LIMIT);
            return jsonb.toJson(byLimit);
        }

        throw new Exception("HelloFindLambda process");
    }

    @Override
    public String getResourceId() {
        return Constants.HELLO_PATH;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}

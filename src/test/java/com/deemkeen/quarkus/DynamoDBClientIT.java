package com.deemkeen.quarkus;

import com.deemkeen.quarkus.handler.DynamoDao;
import com.deemkeen.quarkus.lambda.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@QuarkusTest
class DynamoDBClientIT {

    private static final Logger LOGGER = Logger.getLogger(DynamoDBClientIT.class.getName());

    @Inject
    HelloSaveLambda helloSaveFunction;

    //@Inject
    //HelloDeleteLambda helloDeleteFunction;

    @Inject
    HelloFindLambda helloFindFunction;

    @Inject
    Jsonb jsonb;

    @Inject
    DynamoDao dynamoDao;

    @Test
    void testDynamoHelloSave() throws Exception{
        HelloSaveRequest testRequest = new HelloSaveRequest();
        testRequest.setFirstName("John");
        testRequest.setLastName("Doh");
        String message = jsonb.toJson(testRequest);

        String process = helloSaveFunction.process(message);

        Assert.assertNotNull(process);

        LOGGER.info(process);

    }

    /*@Test
    void testDynamoHelloDelete() {
        HelloDeleteRequest testRequest = new HelloDeleteRequest();
        testRequest.setId("fakeId");
        String message = jsonb.toJson(testRequest);

        helloDeleteFunction.process(message);

    }*/

    @Test
    void testGetDao(){
        List<HelloDynamoResponse> byLimit = dynamoDao.findByLimit(10);
        LOGGER.info(byLimit.toString());

        String singleId = byLimit.get(0).getId();
        HelloDynamoResponse single = dynamoDao.find(singleId);
        LOGGER.info(single.toString());
    }

    @Test
    void testDynamoHelloFind() throws Exception{
        Map<String,String> testMap = new HashMap<>();
        testMap.put("id", "9bd90583-8e39-47df-ad78-3c053a760035");
        String json = jsonb.toJson(testMap);
        String process = helloFindFunction.process(json);
        LOGGER.info(process);
    }

}
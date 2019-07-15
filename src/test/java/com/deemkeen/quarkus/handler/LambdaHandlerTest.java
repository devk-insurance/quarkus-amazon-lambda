package com.deemkeen.quarkus.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.deemkeen.quarkus.lambda.HelloDeleteRequest;
import com.deemkeen.quarkus.lambda.HelloSaveRequest;
import com.deemkeen.quarkus.util.Constants;
import com.deemkeen.quarkus.util.HttpMethod;
import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import java.util.Collections;

@QuarkusTest
class LambdaHandlerTest {

    @Inject
    private Jsonb jsonb;

    @Test
    void testJsonb() {
        String msg = "{\"firstName\":\"test\",\"lastName\":\"test\"}";
        HelloSaveRequest request = jsonb.fromJson(msg, HelloSaveRequest.class);
        System.out.println(request.getFirstName());
    }

    @Test
    void testLambdaHelloSave() {

        final HelloSaveRequest helloSaveRequest = new HelloSaveRequest();
        helloSaveRequest.setFirstName("Foo");
        helloSaveRequest.setLastName("Bar");

        APIGatewayProxyRequestEvent in = new APIGatewayProxyRequestEvent();
        in.setResource(Constants.HELLO_PATH);
        in.setHttpMethod(HttpMethod.POST.name());
        in.setBody(jsonb.toJson(helloSaveRequest));

        APIGatewayProxyResponseEvent out = LambdaClient.invoke(APIGatewayProxyResponseEvent.class, in);
        Assert.assertNotNull(out.getBody());

    }

    @Test
    void testLambdaHelloDelete() {

        final HelloDeleteRequest helloDeleteRequest = new HelloDeleteRequest();
        helloDeleteRequest.setId("b2f41053-0e48-4b63-9906-39525de58a635");

        APIGatewayProxyRequestEvent in = new APIGatewayProxyRequestEvent();
        in.setResource(Constants.HELLO_PATH);
        in.setHttpMethod(HttpMethod.DELETE.name());
        in.setBody(jsonb.toJson(helloDeleteRequest));

        APIGatewayProxyResponseEvent out = LambdaClient.invoke(APIGatewayProxyResponseEvent.class, in);
        //Assert.assertTrue(500 == out.getStatusCode());
    }

    @Test
    void testLambdaHelloFind() {

        APIGatewayProxyRequestEvent in = new APIGatewayProxyRequestEvent();
        in.setResource(Constants.HELLO_PATH);
        in.setQueryStringParameters(Collections.singletonMap("id", "asdasd"));
        in.setHttpMethod(HttpMethod.GET.name());

        APIGatewayProxyResponseEvent out = LambdaClient.invoke(APIGatewayProxyResponseEvent.class, in);
        System.out.println(out.getBody());
    }

    @Test
    void badRequest(){

        APIGatewayProxyRequestEvent in = new APIGatewayProxyRequestEvent();
        in.setResource("/wrong");

        APIGatewayProxyResponseEvent out = LambdaClient.invoke(APIGatewayProxyResponseEvent.class, in);
        Assert.assertTrue(out.getStatusCode().equals(500));
    }

}

package com.deemkeen.quarkus.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.deemkeen.quarkus.util.HttpMethod;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import java.util.HashMap;
import java.util.Map;

import static com.deemkeen.quarkus.util.Constants.WRONG_REQUEST;


public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final LambdaDispatcher dispatcher;

    private final Jsonb jsonb;

    @Inject
    LambdaHandler(LambdaDispatcher dispatcher, Jsonb jsonb){
        this.dispatcher = dispatcher;
        this.jsonb = jsonb;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiRequest, final Context context) {

        String resourceId = apiRequest.getResource();
        String httpMethod = apiRequest.getHttpMethod();

        String body;

        if(HttpMethod.GET.name().equals(httpMethod)) {
            Map<String, String> queryStringParameters = apiRequest.getQueryStringParameters();
            //normalize
            if(queryStringParameters == null){
                queryStringParameters = new HashMap<>();
            }
            body = jsonb.toJson(queryStringParameters);
        } else {
            body = apiRequest.getBody();
        }

        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Credentials", "true");

        apiResponse.setHeaders(headers);

        try {
            String response = dispatcher.dispatch(resourceId, httpMethod, body);
            apiResponse.setBody(response);
            apiResponse.setStatusCode(HttpStatus.SC_OK);
            return apiResponse;
        } catch (Exception ex) {
            String errorText = WRONG_REQUEST + ": " + ex.getMessage();
            apiResponse.setBody("{\"error\":\""+errorText+"\"");
            apiResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return apiResponse;
        }

    }
}

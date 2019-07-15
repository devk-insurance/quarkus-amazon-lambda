package com.deemkeen.quarkus.handler;

import com.deemkeen.quarkus.lambda.HelloDynamoResponse;
import com.deemkeen.quarkus.util.Constants;
import org.apache.http.HttpStatus;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class DynamoDao {

    private final DynamoDbClient client;

    @Inject
    public DynamoDao(DynamoDbClient client) {
        this.client = client;
    }

    public HelloDynamoResponse save(String message) {

        Map<String, AttributeValue> itemValues =
                new HashMap<>();

        String id = UUID.randomUUID().toString();
        long time = new Date().getTime();

        itemValues.put("id", AttributeValue.builder().s(id).build());
        itemValues.put("updatedAt", AttributeValue.builder().n(String.valueOf(time)).build());
        itemValues.put("message", AttributeValue.builder().s(message).build());

        PutItemRequest dbRequest = PutItemRequest.builder()
                .tableName(Constants.TEST_TABLE)
                .item(itemValues)
                .build();

        PutItemResponse response = client.putItem(dbRequest);
        int statusCode = response.sdkHttpResponse().statusCode();
        return new HelloDynamoResponse(id, statusCode, message, time);
    }

    public HelloDynamoResponse delete(String id) {

        Map<String, AttributeValue> idValue =
                new HashMap<>();

        idValue.put("id", AttributeValue.builder().s(id).build());


        // fails if not found by the condition expression
        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().
                tableName(Constants.TEST_TABLE).key(idValue).
                conditionExpression("attribute_exists(id)").
                build();

        DeleteItemResponse response = client.deleteItem(deleteItemRequest);
        int statusCode = response.sdkHttpResponse().statusCode();

        return new HelloDynamoResponse(id, statusCode);
    }

    public HelloDynamoResponse find(String id){

        if(id != null && !id.isEmpty()) {
            Map<String, AttributeValue> idValue =
                    new HashMap<>();

            idValue.put("id", AttributeValue.builder().s(id).build());

            GetItemRequest getItemRequest = GetItemRequest.builder().
                    tableName(Constants.TEST_TABLE).key(idValue).build();

            GetItemResponse response = client.getItem(getItemRequest);

            Map<String, AttributeValue> item = response.item();
            int statusCode = response.sdkHttpResponse().statusCode();

            if(item.size() > 0) {
                AttributeValue message = item.get("message");
                AttributeValue updatedAt = item.get("updatedAt");
                return new HelloDynamoResponse(id, statusCode, message.s(), Long.valueOf(updatedAt.n()));
            }
        }

        return new HelloDynamoResponse(id, HttpStatus.SC_NOT_FOUND);
    }

    public List<HelloDynamoResponse> findByLimit(int limit) {

        List<HelloDynamoResponse> listResponse = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder().
                tableName(Constants.TEST_TABLE).limit(limit).build();

        ScanResponse response = client.scan(scanRequest);
        int statusCode = response.sdkHttpResponse().statusCode();

        response.items().forEach( item -> {
            AttributeValue id = item.get("id");
            AttributeValue message = item.get("message");
            AttributeValue updatedAt = item.get("updatedAt");
            listResponse.add(new HelloDynamoResponse(id.s(), statusCode, message.s(), Long.valueOf(updatedAt.n())));
        });

        return listResponse;

    }


}

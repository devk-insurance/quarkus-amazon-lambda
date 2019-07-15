package com.deemkeen.quarkus;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class LambdaConfiguration {

    @Produces
    @Singleton
    public Jsonb createJsonb(){
        final JsonbConfig config = new JsonbConfig().withFormatting(true);
        return JsonbBuilder.create(config);
    }

    @Produces
    @Singleton
    public DynamoDbClient amazonDynamoDB() {
        final DynamoDbClient build = DynamoDbClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();
        return build;
    }

}

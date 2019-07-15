package com.deemkeen.quarkus.lambda;

import com.deemkeen.quarkus.handler.DynamoDao;

import javax.json.bind.Jsonb;

abstract class AbstractDynamoLambda {

    final Jsonb jsonb;

    final DynamoDao dynamoDao;

    AbstractDynamoLambda(Jsonb jsonb, DynamoDao dynamoDao){
        this.jsonb = jsonb;
        this.dynamoDao = dynamoDao;
    }
}

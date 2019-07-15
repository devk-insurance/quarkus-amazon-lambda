#!/usr/bin/env bash

LAMBDA=http://apigateway-path-to-yourlambda

echo "Creating Entry"

time ENTRY=$(curl -sS -X POST -d '{"firstName":"fizz","lastName":"buzz"}' \
 ${LAMBDA} | jq -r '.id' | cat)

echo $ENTRY

time ENTRY=$(curl -sS -X POST -d '{"firstName":"foo","lastName":"bar"}' \
 ${LAMBDA} | jq -r '.id' | cat)

echo $ENTRY

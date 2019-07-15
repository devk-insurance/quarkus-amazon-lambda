#!/usr/bin/env bash
APPDIR=target
RESDIR=src/main/resources
BUNDLEDIR=target/bundle
#Exchange with your ARN
LAMBDA_ROLE_ARN=arn:aws:iam::XXXXXXXX:role/yourlambdaarn

function bundle() {

    rm -rf ${BUNDLEDIR}
    mvn clean package -Pnative-image -Dnative-image.docker-build=true
    mkdir -p ${BUNDLEDIR}

    cp -r ${APPDIR}/*-runner ${BUNDLEDIR}
    ## You have to copy your cacerts & libsunec.so for the resource dir
    cp -r ${RESDIR}/cacerts ${RESDIR}/bootstrap ${RESDIR}/libsunec.so ${BUNDLEDIR}

    chmod 755 ${BUNDLEDIR}/bootstrap

    cd ${BUNDLEDIR} && zip -q function.zip bootstrap cacerts libsunec.so *-runner ; cd -

}

echo "##### Bundle function #####"

bundle

echo "##### Deploying function #####"

sls deploy



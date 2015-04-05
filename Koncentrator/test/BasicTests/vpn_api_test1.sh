#!/bin/bash

set -ex 
set -o pipefail

rm -R -f /etc/openvpn/easy-rsa/2.0/instances/*

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest | tee /tmp/ca
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest | tee /tmp/server
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest | tee /tmp/profile
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapterTest | tee /tmp/delete





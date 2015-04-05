#!/bin/bash

set -ex 
set -o pipefail

rm -R -f /etc/openvpn/easy-rsa/2.0/instances/*

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest | tee ca
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest | tee server
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest | tee profile
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapterTest | tee delete

chown jenkins:jenkins ca
chown jenkins:jenkins server
chown jenkins:jenkins profile
chown jenkins:jenkins delete



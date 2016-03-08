#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 5

chmod 666 *.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json remote_ip 10.10.10.14
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json local_ip 10.10.10.15

#VPN updateProfile
curl -f -v -X POST "http://127.0.0.1:10002/updateprofile" -d @profile.json

pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"














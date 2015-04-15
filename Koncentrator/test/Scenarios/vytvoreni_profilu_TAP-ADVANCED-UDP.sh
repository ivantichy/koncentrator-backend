#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 5


#CERT generateProfile
wget "http://127.0.0.1:10001/generateprofile/?subvpn_name=tap-advanced-12345&subvpn_type=tap-advanced&common_name=test-hash-1111&domain=tap-advanced-12345.tap-advanced.koncentrator.cz&profile_valid_days=180" -O profile.json -T 10

chmod 666 *.json

#VPN createProfile
curl -v -X PUT "http://digitalocean.ivantichy.cz:10002/createprofile" -d @profile.json

pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
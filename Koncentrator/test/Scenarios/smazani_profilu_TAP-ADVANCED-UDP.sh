#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 5

curl -f -v -X DELETE "http://127.0.0.1:10002/deleteprofile?subvpn_name=tap-advanced-12345&subvpn_type=tap-advanced&common_name=test-hash-1111"

pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"










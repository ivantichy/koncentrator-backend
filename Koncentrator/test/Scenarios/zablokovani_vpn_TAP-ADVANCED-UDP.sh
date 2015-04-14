#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 5

#VPN block subvpn
curl -v -X POST "http://digitalocean.ivantichy.cz:10002/blocksubvpnn" --data '{"subvpn_name" : "tap-advanced-12345", "subvpn_type" : "tap-advanced"}'

pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"










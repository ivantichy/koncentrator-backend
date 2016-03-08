#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

# start api servers

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 8

(echo {}) >  ca.json
chmod 666 ca.json


# user api to add params to ca

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_type tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_name tun-basic-12345
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_range 172.17.1.0/24
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1

#VPN createsubvpn
curl -f -v -X PUT "http://127.0.0.1:10002/createsubvpn" -d @ca.json


#kill api servers
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"










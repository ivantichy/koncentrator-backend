#!/bin/bash

set -ex 
set -o pipefail

set +e
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"
set -e

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerVPN &
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.RunnerCERT &

sleep 5


#CERT createCa 
curl -f -v -X PUT "http://127.0.0.1:10001/createca" --data '{"subvpn_name" : "tap-advanced-12345", "subvpn_type" : "tap-advanced", "domain" : "tap-advanced-12345.tap-advanced.koncentrator.cz", "ca_valid_days" : 3650}' -o ca.json

#CERT generateServer
wget "http://127.0.0.1:10001/generateserver/?subvpn_name=tap-advanced-12345&subvpn_type=tap-advanced&common_name=tap-advanced-12345&domain=tap-advanced-12345.tap-advanced.koncentrator.cz&server_valid_days=3650" -O server.json

chmod 666 *.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_server 123.123.123.123
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_mask 255.255.0.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_device tap1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_management_port 20001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_port 15001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_protocol udp
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_domain_name tap-advanced.koncentrator.cz

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter server.json server_commands ""

#VPN createsubvpn
curl -f -v -X PUT "http://127.0.0.1:10002/createsubvpn" -d @ca.json

#VPN createServer
curl -f -v -X PUT "http://127.0.0.1:10002/createserver" -d @server.json

pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"










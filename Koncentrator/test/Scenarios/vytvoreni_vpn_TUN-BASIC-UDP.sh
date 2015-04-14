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


#CERT createCa 
curl -v -X PUT "http://127.0.0.1:10001/createca" --data '{"subvpn_name" : "tun-basic-12345", "subvpn_type" : "tun-basic", "domain" : "tun-basic-12345.tun-basic.koncentrator.cz", "ca_valid_days" : 3650}' -o ca.json

#CERT generateServer
wget "http://127.0.0.1:10001/generateserver/?subvpn_name=tun-basic-12345&subvpn_type=tun-basic&common_name=tun-basic-12345&domain=tun-basic-12345.tun-basic.koncentrator.cz&server_valid_days=3650" -O server.json

chmod 666 *.json


# user api to add params to ca
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_server 172.17.1.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_mask 255.255.255.128
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_device tun1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_management_port 20001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_port 15001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_protocol udp
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_domain_name tun-basic.koncentrator.cz

# User api to add params to server
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter server.json server_commands ""



#VPN createsubvpn
curl -v -X PUT "http://digitalocean.ivantichy.cz:10002/createsubvpn" -d @ca.json

#VPN createServer
curl -v -X PUT "http://digitalocean.ivantichy.cz:10002/createserver" -d @server.json




#kill api servers
pkill -e -f ".*cz.ivantichy.httpapi.handlers.vpnapi.*"










#!/bin/bash
set -ex 
set -o pipefail
rm *.json
(echo {}) > ca.json
chmod 666 ca.json

# prvni vpn

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tun-basic-12345 tun-basic 

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_type tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_name tun-basic-12345
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_server 123.123.123.123
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_mask 255.255.0.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest2 ca.json


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_remote 10.10.10.10
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_local 10.10.10.11
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json profile_commands ""

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateProfileAdapterTest profile.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.BlockProfileAdapterTest profile.json

# druha vpn


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tun-basic-12346 tun-basic 

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_type tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json subvpn_name tun-basic-12346
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_server 124.123.123.123
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_mask 255.255.0.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest2 ca.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_remote 10.12.10.10
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_local 10.12.10.11
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json profile_commands ""

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateProfileAdapterTest profile.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.alliance.CreateAllianceAdapterTest Koncentrator/test/BasicTests/alliance.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.alliance.BlockAllianceAdapterTest Koncentrator/test/BasicTests/alliance.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.alliance.DeleteAllianceAdapterTest Koncentrator/test/BasicTests/alliance.json








